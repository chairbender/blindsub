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
public class SoundSource {
	private Map<Sound,Source> sounds;
	private Position currentPosition;
	private boolean isListener;
	
	/**
	 * @param initialPosition the initial position for the sounds to be played from
	 * @param isListener, whether this sound set represents the position of the listener (i.e. so internal versions
	 * of sounds should be used).
	 */
	public SoundSource(Position initialPosition,boolean isListener) {
		this.sounds = new HashMap<Sound,Source>();
		this.isListener = isListener;
	}
	
	/**
	 * 
	 * @param toPlay play the sound, which will follow the position of the soundset
     * @param loop whether to loop it. You can stop it with the stop method
	 * @throws IOException if error reading the sound from the file if it needs to create a new buffer
	 */
	public void play(Sound toPlay, boolean loop) throws IOException {
		Source soundSource = sounds.get(toPlay);
		if (soundSource == null) {
			soundSource = SoundEnv.getInstance().addSource(toPlay.getBuffer(isListener));
			sounds.put(toPlay, soundSource);
		}
		soundSource.play(loop);
	}

    public void stop(Sound toStop) {
        Source sourceToStop = sounds.get(toStop);
        sourceToStop.stop();
    }
	
	/**
	 * 
	 * @param newPosition the new position for sounds to be played from. should work even while sounds are playing.
	 */
	public void updatePosition(Position newPosition) {
		for (Source source : sounds.values()) {
			source.setPosition(newPosition.x, newPosition.y, 0);
		}
	}
}
