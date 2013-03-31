package com.kwhipke.blindsub.units;

/**
 * encapsulates degrees
 * @author Kyle
 *
 */
public class Degrees {
	private double degrees;
	public Degrees(double d) {
		this.degrees = d;
	}
	
	public double getDegrees() {
		return degrees;
	}

	/**
	 * adds toAdd to the heading. wraps if lt 0 or gt 360
	 * @param headingChange
	 */
	public void add(Degrees toAdd) {
		this.degrees += toAdd.getDegrees();
		this.degrees = this.degrees % 360;
	}
}
