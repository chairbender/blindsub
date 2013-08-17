package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.physics.CollisionBounds;
import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.sound.SoundEngineController;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.type.SubmarineType;

/**
 * The basic submarine type. Has a status and can get hit and stuff. Exists in a physical place, having
 * an orientation and position. Has a SubmarineType that describes its body and loadout (what model it is, basically).
 *
 */
public abstract class Submarine extends SoundPhysObj  {
	//post refactoring
	SubmarineState currentState;
	SubmarineType submarineType;
	
	public Submarine(SubmarineState initialState, SubmarineType submarineType, SoundEngineController soundEngineController) {
        super(soundEngineController);
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
