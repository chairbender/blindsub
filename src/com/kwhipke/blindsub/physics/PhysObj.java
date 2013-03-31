package com.kwhipke.blindsub.physics;

import java.util.Set;

/**
 * Interface every physics object must implement. Also lets objects update their state
 * the basic measurement of physics time is ticks.
 * @author Kyle
 *
 */
public interface PhysObj {
	
	/**
	 * 
	 * @return the current velocity and heading of the object.
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
	 * ALlows a physics object to update its own state in response to time passing. So, for example, if 100 milliseconds elapse, and the object is a sub that is turning,
	 * the object can update its heading based on its turning radius and how far the steering wheel is turned knowing 100 ms have elapsed.
	 * param elapsedMilliseconds amount of milliseconds object should track as having elapsed during this state update.
	 */
	public void tick(long elapsedMilliseconds);
	
}
