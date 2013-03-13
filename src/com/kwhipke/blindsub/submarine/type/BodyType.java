package com.kwhipke.blindsub.submarine.type;

import com.kwhipke.blindsub.physics.CollisionBounds;
import com.kwhipke.blindsub.submarine.state.SubmarineSpatialState;
import com.kwhipke.blindsub.submarine.state.SubmarineStatus;
import com.kwhipke.blindsub.submarine.stats.BodyCharacteristics;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.submarine.stats.TurningRadius;


/**
 * A type of body (like a model of submarine). The engine and turning stuff is built into the body. The body has a damage resistance, top speed, turning radius (min turning radius), and equipslots.
 * For now, the hit model of all subs is that they are just circles. 
 * @author Kyle
 *
 */
public class BodyType {
	private EquipSlotsDescription equipSlots;
	private BodyCharacteristics characteristics;
	
	/**
	 * 
	 * @param damageResistance how resistant to damage this BodyType is
	 * @param topSpeed
	 */
	public BodyType(BodyCharacteristics characteristics, EquipSlotsDescription equipSlots) {
		this.equipSlots = equipSlots;
		this.characteristics = characteristics;
	}

	/**
	 * 
	 * @return the top speed of the sub
	 */
	public Speed getTopSpeed() {
		return characteristics.getTopSpeed();
	}

	public CollisionBounds getCollisionBounds() {
		return characteristics.getCollisionBounds();
	}

	public TurningRadius getTurningRadius() {
		return characteristics.getTurningRadius();
	}
}
