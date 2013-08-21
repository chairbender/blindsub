package com.kwhipke.blindsub.units;

/**
 * Distance measurement.
 * @author Kyle
 *
 */
public class Meters {
	private double meters;
	
	public Meters(double meters) {
		this.meters = meters;
	}

	public double getMeters() {
		return meters;
	}

    public void add(float displacement) {
        this.meters += displacement;
    }
}
