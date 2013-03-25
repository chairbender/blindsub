package com.kwhipke.blindsub.sound;

import com.kwhipke.blindsub.physics.PhysObj;

/**
 * A physobj that makes sound.
 * @author Kyle
 *
 */
public abstract class SoundPhysicalObject implements PhysObj {
	
	protected SoundEngine soundEngine;
	
	/**
	 * 
	 * @param engine the engine that this object will use to make sounds
	 */
	public SoundPhysicalObject(SoundEngine soundEngine) {
		this.soundEngine = soundEngine;
	}

}
