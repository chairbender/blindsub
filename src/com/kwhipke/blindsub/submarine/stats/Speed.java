package com.kwhipke.blindsub.submarine.stats;

import com.kwhipke.blindsub.submarine.control.ThrottlePosition;
import com.kwhipke.blindsub.submarine.state.SubmarineSpatialState;
import com.kwhipke.blindsub.submarine.state.SubmarineStatus;
import com.kwhipke.blindsub.units.Meters;

/**
 * Speed in meters per second
 * @author Kyle
 *
 */
public class Speed {
	private Meters metersPerSecond;
	
	public Speed(Meters metersPerSecond) {
		this.metersPerSecond = metersPerSecond;
	}
	
	public double getVelocity() {
		return metersPerSecond.getMeters();
	}

	/**
	 * 
	 * @param milliseconds
	 * @return displacement after moving at this speed for this many milliseconds
	 */
	public float displacement(long milliseconds) {
		return (float) (metersPerSecond.getMeters() * milliseconds / 1000.0);
	}
	
	/**
	 * 
	 * @param throttle throttle to use to get the returned speed
	 * @return the speed multiplied by the throttle. So if full throttle will just return whatever this speed is
	 */
	public Speed throttledBy(ThrottlePosition throttle) {
		return new Speed(new Meters(metersPerSecond.getMeters()*throttle.getThrottleFactor()));
	}
}
