package com.kwhipke.blindsub.submarine.control;

/**
 * Indicates a position for the throttle stick. 0 indicates neutral. 1 indicates full. -1 indicates full reverse.
 * @author Kyle
 *
 */
public class ThrottlePosition {
	double throttle;
	public ThrottlePosition(double throttle) {
		this.throttle = throttle;
	}
	
	/**
	 * 
	 * @return the throttling factor (0 = stopped, 1 = full -1 = full reverse)
	 */
	public double getThrottleFactor() {
		return throttle;
	}
}
