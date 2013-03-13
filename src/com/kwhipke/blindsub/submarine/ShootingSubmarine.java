package com.kwhipke.blindsub.submarine;

import java.io.IOException;

import org.pielot.openal.SoundEnv;
import org.pielot.openal.Source;

import android.app.Activity;

import com.kwhipke.blindsub.*;
import com.kwhipke.blindsub.physics.Heading;
import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.physics.PhysicsEngine;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.sound.SoundPhysicalObject;
import com.kwhipke.blindsub.submarine.state.SubmarineSpatialState;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.state.SubmarineStatus;
import com.kwhipke.blindsub.submarine.type.SubmarineType;
import com.kwhipke.blindsub.util.PhysicsUtil;

/**
 * Submarine that shoots missiles, i.e. needs access to physics engine. Has a fire method.
 *
 */
public abstract class ShootingSubmarine extends Submarine {
	private PhysicsEngine physicsEngine;
	
	public ShootingSubmarine(SubmarineState initialState, SoundEngine soundEngine, SubmarineType submarineType, PhysicsEngine physicsEngine) {
		super(initialState,soundEngine,submarineType);
		this.physicsEngine = physicsEngine;
	}
	
	/**
	 * 
	 * @param direction direction to shoot the missile from
	 */
	protected void fire(Heading direction) {
		//TODO: Create missile class. Get missile from submarine type
	}
	
	
}
