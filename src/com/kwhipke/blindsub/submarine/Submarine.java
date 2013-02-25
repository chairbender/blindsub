package com.kwhipke.blindsub.submarine;

import java.io.IOException;

import org.pielot.openal.SoundEnv;
import org.pielot.openal.Source;

import android.app.Activity;

import com.kwhipke.blindsub.*;
import com.kwhipke.blindsub.physics.SubmarineState;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.util.PhysicsUtil;

/**
 * The basic submarine type. Has health and can get hit and stuff.
 * @author Kyle
 *
 */
public abstract class Submarine implements PhysObj {

	private static final double COLLISION_RADIUS = 5.0;
	//The maximum health of the submarine.
	private static final double MAX_HEALTH = 10;
	
	protected Source hit;

	private double health;
	
	public Submarine(SubmarineState initialState, SoundEngine soundEngine) {
		//Initialize the health and getting hit sound
		this.health = MAX_HEALTH;
		try {
			hit = soundEngine.createSource("hit");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

	@Override
	public double getRadius() {
		return COLLISION_RADIUS;
	}

	/**
	 * Resolve a collision with the other object.
	 * If it was a bullet collision, will invoke resolveBulletHit
	 * @param other
	 * @param velocity velocity of this submarine
	 * @param heading heading of this submarine
	 */
	protected boolean resolveCollision(PhysObj other, double velocity, double heading) {
		//Determine if they DO collide
		//I'm setting the origin as this object's x and y coordinates
		double overlap = PhysicsUtil.getOverlap(this, other);

		if (overlap > 0) {
			if (other instanceof Bullet) {
				return resolveBulletHit((Bullet) other);
			} else {
				if (velocity == 0) {
					velocity = 1;
				}
					//Collision. So move this object back so it just touches the other object
					double reverseVelocityX = -PhysicsUtil.getVelocityComponents(heading,velocity)[0];
					double reverseVelocityY = -PhysicsUtil.getVelocityComponents(heading,velocity)[1];
					//Want to translate along this vector until distance >= totalRadii.
					double t = overlap / Math.sqrt((reverseVelocityY*reverseVelocityY +
							reverseVelocityX*reverseVelocityX));
					this.setX(this.getPosition()[0] + reverseVelocityX * t);
					this.setY(this.getPosition()[1] + reverseVelocityY * t);
			}
		}	
		return false;
	}
	
	
	/**
	 * Decrease this sub's health by d
	 * @param d
	 */
	protected void setHealth(double d) {
		health = d;
	}

	/**
	 * get this subs current health
	 * @return
	 */
	protected double getHealth() {
		return health;
	}


	/*
	 * set the x value of this submarine to the specified coordinates
	 */
	protected abstract void setX(double x);
	protected abstract void setY(double y);
	/**
	 * What to do when it gets hit.
	 * @param bullet
	 * @return true if the sub is destroyed
	 */
	protected abstract boolean resolveBulletHit(Bullet bullet);
}
