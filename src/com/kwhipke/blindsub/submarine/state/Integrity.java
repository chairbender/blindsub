package com.kwhipke.blindsub.submarine.state;

/**
 * Indicates the structural integrity of a sub. 0 means completely wrecked. 1.00 means perfect.
 * @author Kyle
 *
 */
public class Integrity {
	public static final Integrity MAXIMUM = new Integrity(1);
	public double integrity;
	
	public Integrity(double integrity) {
		this.integrity = integrity;
	}
}
