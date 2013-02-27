package com.kwhipke.blindsub.submarine.control;

/**
 * A position for the steering wheel to be in.
 * 1 indicates farthest right. 0 indicates centered. -1 indicates farthest left. Should only ever be between
 * -1 and 1 inclusive.
 * @author Kyle
 *
 */
public class Steering {
	public double steering;
	
	public Steering(double steering) {
		this.steering = steering;
	}
	
}
