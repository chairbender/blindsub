package com.kwhipke.blindsub.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.pielot.openal.SoundEnv;
import org.pielot.openal.Source;

import com.kwhipke.blindsub.physics.Position;

/**
 * Represents a collection of sounds that are all played from the same position and
 * can be played and stopped. And the position can be changed. They use the global buffer manager and sound environment
 * to play and load sounds. Currently, cannot play the same sound over itself (i,e, it retriggers when play is called)
 * @author Kyle
 *
 */
public class SoundSet {
	private Map<Sound,Source> sounds;
	private Position currentPosition;
	
	/**
	 * @param initialPosition the initial position for the sounds to be played from
	 */
	public SoundSet(Position initialPosition) {
		this.sounds = new HashMap<Sound,Source>();
	}
	
	/**
	 * 
	 * @param toPlay play the sound, which will follow the position of the soundset
	 * @throws IOException if error reading the sound from the file if it needs to create a new buffer
	 */
	public void play(Sound toPlay) throws IOException {
		Source soundSource = sounds.get(toPlay);
		if (soundSource == null) {
			soundSource = SoundEnv.getInstance().addSource(toPlay.getBuffer());
			sounds.put(toPlay, soundSource);
		}
		soundSource.play(false);
	}
	
	/**
	 * 
	 * @param newPosition the new position for sounds to be played from. should work even while sounds are playing
	 */
	public void updatePosition(Position newPosition) {
		for (Source source : sounds.values()) {
			source.setPosition(newPosition.x, newPosition.y, 0);
		}
	}
}
