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
}
