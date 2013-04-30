package com.kwhipke.blindsub.submarine.type;

import com.kwhipke.blindsub.submarine.Damage;

/**
 * Describes how resistant to damage something is. should be a number between 0 and 1.
 * 0 means absolutely no damage resistance, 1 means takes no damage ever.
 * @author Kyle
 *
 */
public class DamageResistance {
	public double damageResistance;
	
	/**
	 * 
	 * @param damageResistance a value between 0 and 1. 
	 * 0 means absolutely no damage resistance, 1 means takes no damage ever.
	 */
	public DamageResistance(double damageResistance) {
		this.damageResistance = damageResistance;
	}
	
	/**
	 * 
	 * @param toReduce damage to reduce
	 * @return apply the damage resistance to toReduce and return the reduced damage
	 */
	public Damage reduceDamage(Damage toReduce) {
		return new Damage(toReduce.getIntegritySubtracted() * (1 - damageResistance));
	}
	
}
