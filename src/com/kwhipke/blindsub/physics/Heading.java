package com.kwhipke.blindsub.physics;

/**
 * Encapsulates a heading. 0 is east 90 is north
 * @author Kyle
 *
 */
public class Heading {
	private float heading;
	
	public Heading(float heading) {
		this.heading = heading;
	}
	
	public float getDegrees() {
		return heading;
	}
	
	public float getRadians() {
		return (float) (heading * Math.PI / 180.0);
	}
	
}
