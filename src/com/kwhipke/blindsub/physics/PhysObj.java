package com.kwhipke.blindsub.physics;

import java.util.Set;

/**
 * Interface every physics object must implement. Physics works like this - objects can change their velocity vector and collision bounds
 * whenever they want - it's not really a robust physics engine since it doesn't treat everything in terms of forces acting on objects, but it doesn't need to.
 * When objects collide, it'll prevent them from moving through each other unless one is transparent.
 * @author Kyle
 *
 */
public interface PhysObj {
	
	/**
	 * 
	 * @return the current desired velocity and heading of the object.
     * The physics engine will let things just change this however they want. If an object collides with another object, though
     * it will not allow them to pass through each other unless one of them is transparent
	 */
	public VelocityVector getVelocityVector();
	
	/**
	 * Lets the object know a collision has occurred. Each object is only responsible for their own state
	 * and strictly forbidden from messing with the other object's state. This method
	 * will be invoked on both objects when a collision happens.
	 * @param other
	 * @return true if the object should be removed from the simulation. false otherwise
	 */
	public boolean doCollision(PhysObj other);

	/**
	 * 
	 * @return the collision bounds of the object
	 */
	public CollisionBounds getCollisionBounds();


    /**
     * lets a sub know that it should update its state for elapsedMilliseconds milliseconds. In other words,
     * if it passes 100ms, after this method exits, the submarine's state should represent what it will be like in 100ms from
     * when the method entered.
     * @param elapsedMilliseconds amount of milliseconds to elapse
     * @param physicsEngineController the controller allowing the object to interact with the physics engine
     */
    public void tick(long elapsedMilliseconds, PhysicsEngineController physicsEngineController);
}
