package com.kwhipke.blindsub.submarine;

import java.io.IOException;

import org.pielot.openal.SoundEnv;
import org.pielot.openal.Source;

import android.app.Activity;

import com.kwhipke.blindsub.*;
import com.kwhipke.blindsub.physics.CollisionBounds;
import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.sound.SoundPhysicalObject;
import com.kwhipke.blindsub.submarine.control.Steering;
import com.kwhipke.blindsub.submarine.state.SubmarineSpatialState;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.state.SubmarineStatus;
import com.kwhipke.blindsub.submarine.type.SubmarineType;
import com.kwhipke.blindsub.units.Degrees;
import com.kwhipke.blindsub.units.Meters;
import com.kwhipke.blindsub.util.PhysicsUtil;

/**
 * The basic submarine type. Has a status and can get hit and stuff. Exists in a physical place, having
 * an orientation and position. Might make sounds. Has a SubmarineType that describes its body and loadout (what model it is, basically)
 *
 */
public abstract class Submarine extends SoundPhysicalObject {
	//post refactoring
	SubmarineState currentState;
	SubmarineType submarineType;
	
	public Submarine(SubmarineState initialState, SoundEngine soundEngine, SubmarineType submarineType) {
		super(soundEngine);
		this.currentState = initialState;
		this.submarineType = submarineType;
	}
	
	@Override
	public boolean doCollision(PhysObj other) {
		//When it collides with a missile, it should reduce hull integrity
		if (other instanceof Torpedo) {
			//Take damage, calculated based on the type of submarine this is and the
			//torpedo
			currentState.takeDamage(submarineType,(Torpedo)other);
		}
		
		return currentState.isDestroyed();
	}
	
	@Override
	public CollisionBounds getCollisionBounds() {
		return submarineType.getCollisionBounds();
	}	

}
