package com.kwhipke.blindsub.physics;

import java.util.HashSet;
import java.util.Set;


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
	private boolean isPaused = false;
	private final long millisecondsPerTick;
	Set<TrackedPhysicalObject> trackedObjects;

	/**
	 * 
	 * @param millisecondsPerTick - how many real milliseconds should elapse per tick.
	 */
	public PhysicsEngine(long millisecondsPerTick) {
		this.millisecondsPerTick = millisecondsPerTick;
		this.trackedObjects = new HashSet<TrackedPhysicalObject>();
	}
	
	/**
	 * 
	 * @param toAdd physics object to start tracking in this simulation. not safe to call while ticking, currently.
	 */
	public void addObject(PhysObj toAdd, Position startingPosition) {
		trackedObjects.add(new TrackedPhysicalObject(toAdd,startingPosition));
	}
	
	/**
	 * Advance the simulation one tick - all objects will be moved at their current velocities for millisecondsPerTick milliseconds. After that move is done, 
	 * the system will check for collisions. So, a low tick rate might potentially let objects pass through each other
	 */
	private void tick() {
		/*
		 * For each object, get its speed and direction. Then calculate its new position after millisPerTick milliseconds.
		 * Set the new position of it. Chek for collissions. If it collides with another object, move it back to where it was and run the collision handler.
		 */
		for (TrackedPhysicalObject obj : trackedObjects) {
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
	}
	
	/**
	 * resolve collisions between obj and others
	 * @param obj
	 * @param others
	 */
	private void triggerCollisions(TrackedPhysicalObject obj, Set<TrackedPhysicalObject> others) {
		for (TrackedPhysicalObject other : others) {
			obj.doCollision(other);
			other.doCollision(obj);
		}
	}
	
	//if obj collides with anything else add it to the set and return it
	private Set<TrackedPhysicalObject> checkCollisions(TrackedPhysicalObject obj) {
		Set<TrackedPhysicalObject> result = new HashSet<TrackedPhysicalObject>();
		for (TrackedPhysicalObject other : trackedObjects) {
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
					tick();
					try {
						Thread.sleep(millisecondsPerTick);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
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
}
