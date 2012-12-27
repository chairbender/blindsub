package com.kwhipke.blindsub;

/**
 * Interface every physics object must implement
 * the basic measurement of physics is ticks.
 * @author Kyle
 *
 */
public interface PhysObj {
	
	/**
	 * Update this object's state.
	 * @param elapsedMillis the milliseconds that have elapsed between this tick
	 * and the previous tick. If first tick, is 0.
	 */
	public void tick(long elapsedMillis);
	
	/**
	 * return the x and y coordinates of the object
	 * x is index 0 y index 1
	 */
	public double[] getPosition();
	
	/**
	 * 
	 * @return the radius of the object's collision circle. Assuming
	 * every object is just a circle for simplicity right now.
	 */
	public double getRadius();
	
	/**
	 * Resolve the collision. The object this method is invoked on should NOT
	 * modify the state of other. Each object is only responsible for their own state
	 * and strictly forbidden from messing with the other object's state. This method
	 * will be invoked on both objects.
	 * @param other
	 * @return true if this object should be destroyed
	 */
	public boolean resolveCollision(PhysObj other);
}
