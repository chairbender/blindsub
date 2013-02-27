package com.kwhipke.blindsub.submarine.type;


/**
 * A type of body (like a model of submarine). Describes the damage model of the submarine for now.
 * All submarines don't have health - they have hull integrity.
 * @author Kyle
 *
 */
public class BodyType {
	private DamageResistance damageResistance;
	/**
	 * 
	 * @param damageResistance how resistant to damage this BodyType is
	 */
	public BodyType(DamageResistance damageResistance) {
		this.damageResistance = damageResistance;
	}
}
