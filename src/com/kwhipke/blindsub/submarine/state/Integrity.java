package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.submarine.Damage;

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
	
	public void takeDamage(Damage toTake) {
		integrity -= toTake.getIntegritySubtracted();
	}

	/*
	 * @return true if integrity has been reduced to zero (or below)
	 */
	public boolean isZero() {
		return integrity <= 0;
	}
}
