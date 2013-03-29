package com.kwhipke.blindsub.physics;

import com.kwhipke.blindsub.units.Degrees;

/**
 * Encapsulates a heading. 0 is east 90 is north
 * @author Kyle
 *
 */
public class Heading {
	private Degrees heading;
	
	public Heading(float heading) {
		this.heading = new Degrees(heading);
	}
	
	public Heading(Degrees heading) {
		this.heading = heading;
	}
	
	public Degrees getDegrees() {
		return heading;
	}
	
	public float getRadians() {
		return (float) (heading.getDegrees() * Math.PI / 180.0);
	}

	/**
	 * 
	 * @param headingChange add headingCHange to the facing (even if it is negative).
	 * wraps around if less than zero or greater than 360. So it behaves as you would expect for a heading
	 */
	public void adjustBy(Degrees headingChange) {
		heading.add(headingChange);
	}
	
}
