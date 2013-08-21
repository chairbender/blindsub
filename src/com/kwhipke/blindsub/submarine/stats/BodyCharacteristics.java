package com.kwhipke.blindsub.submarine.stats;

import com.kwhipke.blindsub.physics.bounds.CircularBounds;
import com.kwhipke.blindsub.physics.bounds.CollisionBounds;
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
	private CollisionBounds collisionBounds;
	
	public static final BodyCharacteristics BASIC = new BodyCharacteristics(new DamageResistance(.2),new Speed(new Meters(5)),new TurningRadius(new Meters(10)), new CircularBounds(new Meters(2.5)));
	
	public BodyCharacteristics(DamageResistance damageResistance, Speed topSpeed, TurningRadius minTurningRadius, CollisionBounds bodyRadius) {
		this.damageResistance = damageResistance;
		this.topSpeed = topSpeed;
		this.minTurningRadius = minTurningRadius;
		this.collisionBounds = bodyRadius;
	}

	public Speed getTopSpeed() {
		return topSpeed;
	}

	public CollisionBounds getCollisionBounds() {
		return collisionBounds;
	}

	public TurningRadius getTurningRadius() {
		return minTurningRadius;
	}

	public DamageResistance getDamageResistance() {
		return damageResistance;
	}
	
}
