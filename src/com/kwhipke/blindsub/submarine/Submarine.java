package com.kwhipke.blindsub.submarine;

import java.io.IOException;

import org.pielot.openal.SoundEnv;
import org.pielot.openal.Source;

import android.app.Activity;

import com.kwhipke.blindsub.*;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.sound.SoundPhysicalObject;
import com.kwhipke.blindsub.submarine.state.SubmarineSpatialState;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.state.SubmarineStatus;
import com.kwhipke.blindsub.submarine.type.SubmarineType;
import com.kwhipke.blindsub.util.PhysicsUtil;

/**
 * The basic submarine type. Has a status and can get hit and stuff. Exists in a physical place, having
 * an orientation and position. Might make sounds. Has a SubmarineType that describes its body and loadout (what model it is, basically)
 *
 */
public abstract class Submarine extends SoundPhysicalObject {

	private static final double COLLISION_RADIUS = 5.0;
	//The maximum health of the submarine.
	private static final double MAX_HEALTH = 10;
	
	protected Source hit;

	private double health;
	
	//post refactoring
	SubmarineState currentState;
	SubmarineType submarineType;
	
	public Submarine(SubmarineState initialState, SoundEngine soundEngine, SubmarineType submarineType) {
		super(soundEngine);
		this.currentState = initialState;
		this.submarineType = submarineType;
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
