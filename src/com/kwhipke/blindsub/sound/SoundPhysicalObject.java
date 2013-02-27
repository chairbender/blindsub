package com.kwhipke.blindsub.sound;

import com.kwhipke.blindsub.PhysObj;

/**
 * A physobj that makes sound.
 * @author Kyle
 *
 */
public abstract class SoundPhysicalObject implements PhysObj {
	
	private SoundEngine soundEngine;
	
	/**
	 * 
	 * @param engine the engine that this object will use to make sounds
	 */
	public SoundPhysicalObject(SoundEngine soundEngine) {
		this.soundEngine = soundEngine;
	}

}
