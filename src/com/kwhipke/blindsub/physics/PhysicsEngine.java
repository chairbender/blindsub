package com.kwhipke.blindsub.physics;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kwhipke.blindsub.engine.Ticker;


/**
 * Encapsualtes handling the phjysical interactions between
 * all of the objects in a running game. Lets other classes know when
 * certain events happen (so the gamestate can be updated or audio can be played)
 * 
 * The physics engine's "tick" method updates the state of all objects. It is sensitive to the amount of time elapsed between calls.
 * So, if an object is traveling at 5 meters per second, and the interval between ticks is .5 real-world seconds, the object will
 * still be moved. However, object collision is only checked at the END of an object's movement during a tick. And, if the object's state
 * changes after the beginning of it's movement during a tick, the state will not be picked up until the next tick. So, a quicker tick rate
 * means more responsiveness but more resource intensiveness.
 *
 * Basically, imagine each tick as a "draw" for the physics engine.
 * @author Kyle
 *
 */
public class PhysicsEngine {
    private static final double SOUND_METERS_PER_SECOND = 50;
    private boolean isPaused = false;
	private final long millisecondsPerTick;
	Map<PhysObj,TrackedPhysicalObject> trackedObjects;
	private Ticker doAfterTick;

    /**
	 * 
	 * @param millisecondsPerTick - how many real milliseconds should elapse per tick.
	 */
	public PhysicsEngine(long millisecondsPerTick) {
		this.millisecondsPerTick = millisecondsPerTick;
		this.trackedObjects = new HashMap<PhysObj,TrackedPhysicalObject>();
	}
	
	/**
	 * 
	 * @param toAdd physics object to start tracking in this simulation. not safe to call while ticking, currently.
	 */
	public void addObject(PhysObj toAdd, Position startingPosition) {
		trackedObjects.put(toAdd,new TrackedPhysicalObject(toAdd,startingPosition));
	}

	/**
	 * Advance the simulation one tick - all objects will be moved at their current velocities for millisecondsPerTick milliseconds. After that move is done,
	 * the system will check for collisions. Then, finally, the objects will be ticked (their tick method will be called)
     * So, a long tick interval might potentially let objects pass through each other
	 * @throws IOException 
	 */
	private void tick() throws IOException {
		/*
		 * For each object, get its speed and direction. Then calculate its new position after millisPerTick milliseconds.
		 * Set the new position of it. Check for collissions. If it collides with another object, move it back to where it was and run the collision handler.
		 */
		for (TrackedPhysicalObject obj : trackedObjects.values()) {
			VelocityVector velocityVector = obj.getVelocityVector();
			Position oldPosition = obj.getPosition();
			Position newPosition = velocityVector.displacement(millisecondsPerTick);
			obj.setPosition(oldPosition.add(newPosition));
			Set<TrackedPhysicalObject> collisions = checkCollisions(obj);
			if (collisions.size() > 0) {
				obj.setPosition(oldPosition);
				triggerCollisions(obj,collisions);
			}
		}
        //Call all the tick methods
        for (TrackedPhysicalObject obj : trackedObjects.values()) {
            obj.getPhysObj().tick(millisecondsPerTick,new PhysicsEngineController(this,obj.getPhysObj()));
        }
		if (doAfterTick != null) {
			doAfterTick.tick();
			
		}
	}
	
	/**
	 * resolve collisions between obj and others
	 * @param obj
	 * @param others
	 */
	private void triggerCollisions(TrackedPhysicalObject obj, Set<TrackedPhysicalObject> others) {
		Set<TrackedPhysicalObject> toRemove = new HashSet<TrackedPhysicalObject>();
		for (TrackedPhysicalObject other : others) {
			if (obj.doCollision(other)) {
				toRemove.add(obj);
			}
			if (other.doCollision(obj)) {
				toRemove.add(other);
			}
		}
		
		for (TrackedPhysicalObject toDelete : toRemove) {
			trackedObjects.remove(toDelete.getPhysObj());
		}
	}
	
	//if obj collides with anything else add it to the set and return it
	private Set<TrackedPhysicalObject> checkCollisions(TrackedPhysicalObject obj) {
		Set<TrackedPhysicalObject> result = new HashSet<TrackedPhysicalObject>();
		for (TrackedPhysicalObject other : trackedObjects.values()) {
			if (other != obj) {
				CollisionBounds objBounds = obj.getCollisionBounds();
				CollisionBounds otherBounds = obj.getCollisionBounds();
				Position otherPosition = other.getPosition();
				if (objBounds.checkCollision(obj.getPosition(), otherPosition, otherBounds)) {
					result.add(other);
				}
			}
		}
		return result;
	}
	
	/**
	 * Begin simulating and ticking. Happens on a separate from where it is invoked.
	 */
	public void start() {
		isPaused = false;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!isPaused()) {
                    try {
                        tick();
						Thread.sleep(millisecondsPerTick);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	/**
	 * 
	 * @param toPosition PhysObj to get the position of
	 * @return the position of the PhysObj tracked by this physics engine that equals toPosition (equals by reference, so it must
	 * equal the PhysObj you passed in in the addObjet method). Null if none found
	 */
	public Position getPositionOfPhysObj(PhysObj toPosition) {
		return trackedObjects.get(toPosition) == null ? null : trackedObjects.get(toPosition).getPosition();
	}
	
	/**
	 * stop simulating. freeze everything
	 */
	public synchronized void stop() {
		isPaused = true;
	}
	
	private synchronized boolean isPaused() {
		return isPaused;
	}

	/**
	 * 
	 * @param toTick what should be ticked after the physics engine's tick is complete.
	 */
	public void setPostTickEvent(Ticker toTick) {
		this.doAfterTick = toTick;
		
	}

    /**
     *
     * @return the ojects that would echo back a ping from the sourceObject
     */
    public Set<PhysObj> getEchoObjects(PhysObj sourceObj) {
        Set<PhysObj> result = new HashSet<PhysObj>();
        for (PhysObj physObj : this.trackedObjects.keySet()) {
            if (physObj != sourceObj) {
                result.add(physObj);
            }
        }
        return result;
    }

    /**
     *
     * @param source
     * @param echoObject
     * @return time in ms for sound to travel from source to echoObject, assuming no obstructions
     */
    public long getTravelTime(PhysObj source, PhysObj echoObject) {
        TrackedPhysicalObject sourceTrackedObj = trackedObjects.get(source);
        TrackedPhysicalObject echoTrackedObject = trackedObjects.get(echoObject);
        return Math.round(sourceTrackedObj.getPosition().distanceTo(echoTrackedObject.getPosition()).getMeters() / SOUND_METERS_PER_SECOND * 1000);

    }
}
