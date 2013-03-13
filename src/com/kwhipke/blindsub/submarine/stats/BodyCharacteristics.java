package com.kwhipke.blindsub.submarine.stats;

import com.kwhipke.blindsub.physics.CollisionBounds;
import com.kwhipke.blindsub.submarine.type.DamageResistance;
import com.kwhipke.blindsub.units.Meters;

/**
 * A submarine body's turning radius, speed, resistance and body radius (since we are assuming engine and
 * fins are built into the body).
 * @author Kyle
 *
 */
public class BodyCharacteristics {
	private DamageResistance damageResistance;
	private Speed topSpeed;
	private TurningRadius minTurningRadius;
	private CollisionBounds bodyRadius;
	
	public static final BodyCharacteristics BASIC = new BodyCharacteristics(new DamageResistance(.2),new Speed(new Meters(5)),new TurningRadius(new Meters(10)), new CollisionBounds(new Meters(5)));
	
	public BodyCharacteristics(DamageResistance damageResistance, Speed topSpeed, TurningRadius minTurningRadius, CollisionBounds bodyRadius) {
		this.damageResistance = damageResistance;
		this.topSpeed = topSpeed;
		this.minTurningRadius = minTurningRadius;
		this.bodyRadius = bodyRadius;
	}

	public Speed getTopSpeed() {
		return topSpeed;
	}

	public CollisionBounds getCollisionBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public TurningRadius getTurningRadius() {
		return minTurningRadius;
	}
	
}
