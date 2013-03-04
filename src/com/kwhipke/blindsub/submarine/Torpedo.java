package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.util.PhysicsUtil;

/**
 * 
 * @author Kyle
 *
 */
public class Torpedo extends Bullet{

	private static final double DAMAGE = 5.0;
	private static final double METERS_PER_SECOND = 20;
	private static final double RADIUS = 1.5;
	
	private double x, y;
	private double heading;
	
	/**
	 * 
	 * @param startX
	 * @param startY
	 * @param heading in degrees (0 being east 90 being north)
	 * @param creator the creator of this object
	 */
	public Torpedo(double startX, double startY, double heading, PhysObj creator) {
		super(creator);
		this.x = x;
		this.y = y;
		this.heading = heading;
	}
	
	@Override
	public void tick(long elapsedMillis) {
		double elapsedSeconds = elapsedMillis / 1000.0;
		//Move it in the direction of its heading
		double[] velocityVec = PhysicsUtil.getVelocityComponents(heading, METERS_PER_SECOND);
		this.x += elapsedSeconds * velocityVec[0];
		this.y += elapsedSeconds * velocityVec[1];
		
	}
	
	public double getDamage() {
		return DAMAGE;
	}

	@Override
	public double[] getPosition() {
		double result[] = new double[2];
		result[0] = this.x;
		result[1] = this.y;
		return result;
	}

	@Override
	public double getRadius() {
		return RADIUS;
	}

	@Override
	public boolean resolveCollision(PhysObj other) {
		//Destroy this object if it collides with others
		//Only responsible for its own state!
		if (PhysicsUtil.getOverlap(this, other) > 0) {
			return true;
		} else {
			return false;
		}
		
	}

}
