package com.kwhipke.blindsub.sound;

import java.io.IOException;

import com.kwhipke.blindsub.physics.PhysObj;

/**
 * Encapsulates a request to play a sound after a certain amount of time.
 * @author Kyle
 *
 */
public class PlaySoundDelayed {

	private long destinationTime;
	private Sound toPlay;
	private PhysObj source;

	/**
	 * 
	 * @param source object to play sound from
	 * @param toPlay sound to play
	 * @param delay how many milliseconds from this constructor invocation to play the sound
	 */
	public PlaySoundDelayed(PhysObj source,Sound toPlay,long delay) {
		this.source = source;
		this.toPlay = toPlay;
		this.destinationTime = delay + System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @param soundEngine soundEngine to use to play the sound
	 * plays the sound if it is >= its requested time to play
	 * @return true if the sound started to play 
	 * @throws IOException 
	 */
	public boolean playIfReady(SoundEngine soundEngine) throws IOException {
		if (System.currentTimeMillis() >= destinationTime) {
			soundEngine.playSound(toPlay, source);
			return true;
		}
		return false;
		
	}

}
