package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.physics.*;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.units.Meters;
import com.kwhipke.blindsub.util.PhysicsUtil;

/**
 * 
 * @author Kyle
 *
 */
public class Torpedo implements PhysObj, DamagingObject{

	private static final Damage DAMAGE = new Damage(5.0);
	private static final Speed SPEED = new Speed(new Meters(30));
	private static final Meters RADIUS = new Meters(1.5);
	
	private Heading heading;
	private PhysObj creator;
	private VelocityVector vector;
	/**
	 * 
	 * @param startX
	 * @param startY
	 * @param heading in degrees (0 being east 90 being north)
	 * @param creator the creator of this object
	 */
	public Torpedo(Heading initialHeading, PhysObj creator) {
		this.heading = initialHeading;
		this.creator = creator;
		this.vector = new VelocityVector(SPEED,initialHeading);
	}

	@Override
	public Damage getDamage() {
		return DAMAGE;
	}

	@Override
	public VelocityVector getVelocityVector() {
		return vector;
	}

	@Override
	public boolean doCollision(PhysObj other) {
		if (other != creator) {
			return true;
		}
		return false;
	}

	@Override
	public CollisionBounds getCollisionBounds() {
		return new CollisionBounds(RADIUS);
	}

	@Override
	public void tick(long elapsedMilliseconds, PhysicsEngineController controller) {
		// do nothing
		
	}

}
