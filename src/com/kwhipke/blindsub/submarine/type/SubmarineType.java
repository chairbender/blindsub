package com.kwhipke.blindsub.submarine.type;

import com.kwhipke.blindsub.physics.bounds.CollisionBounds;
import com.kwhipke.blindsub.submarine.Damage;
import com.kwhipke.blindsub.submarine.Torpedo;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.submarine.stats.TurningRadius;


/**
 * Describes a type of submarine (like, a model of submarine).
 * Includes tje submarine's body (what it's made of and what it can hold) and 
 * loadout (how the body is outfitted with weapons and devices)
 * @author Kyle
 *
 */
public class SubmarineType {
	private BodyType bodyType;
	private Loadout loadout;
	
	public SubmarineType(BodyType bodyType, Loadout loadout) {
		this.bodyType = bodyType;
		this.loadout = loadout;
	}

	/**
	 * 
	 * @return the top speed of the submarine
	 */
	public Speed getTopSpeed() {
		return bodyType.getTopSpeed();
	}
	

	/**
	 * 
	 * @return the collision model of the submarine
	 */
	public CollisionBounds getCollisionBounds() {
		return bodyType.getCollisionBounds();
	}

	public TurningRadius getTurningRadius() {
		return bodyType.getTurningRadius();
	}

	/**
	 * 
	 * @param torpedo
	 * @return the amount of damage that would be done if this sub was hit with the torpedo
	 */
	public Damage getDamageDone(Torpedo torpedo) {
		return bodyType.reduceDamage(torpedo);
	}
}
