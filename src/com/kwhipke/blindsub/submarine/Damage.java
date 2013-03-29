package com.kwhipke.blindsub.submarine;

/**
 * describes how something gets damaged
 * @author Kyle
 *
 */
public class Damage {
	private double damage;
	
	/**
	 * 
	 * @param damage amount of integrity subtracted per hit
	 */
	public Damage(double damage) {
		this.damage = damage;
	}
	
	public double getIntegritySubtracted() {
		return damage;
	}
}
