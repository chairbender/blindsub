package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.physics.*;
import com.kwhipke.blindsub.physics.bounds.CircularBounds;
import com.kwhipke.blindsub.physics.bounds.CollisionBounds;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.units.Meters;

/**
 * 
 * @author Kyle
 *
 */
public class Torpedo implements PhysObj, DamagingObject{

	private static final Damage DAMAGE = new Damage(5.0);
	private static final Speed SPEED = new Speed(new Meters(30));
	private static final CollisionBounds COLLISION_BOUNDS = new CircularBounds(new Meters(1.5));

	private PhysObj creator;
	private VelocityVector vector;
	/**
	 * 
	 * @param initialHeading the initial heading of the torpedo
	 * @param creator the creator of this object
	 */
	public Torpedo(Heading initialHeading, PhysObj creator) {
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
		return COLLISION_BOUNDS;
	}

	@Override
	public void tick(long elapsedMilliseconds, PhysicsEngineController controller) {
		// do nothing
		
	}

    @Override
    public boolean collidesWith(PhysObj other) {
        //Don't collide with the sub that fired me
        return creator != other;
    }

    /**
     *
     * @param submarine submarine to check if it fired this torpedo
     * @return true if submarine fired this torpedo
     */
    public boolean wasCreatedBy(Submarine submarine) {
        return creator == submarine;
    }
}
