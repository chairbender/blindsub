package com.kwhipke.blindsub.sound;

import java.io.IOException;

import org.pielot.openal.Buffer;

/**
 * Represents a sound that can be played. Sounds are referenced by name.
 * Just look in the "assets" folder and remove the .wav to get the name of the sound.
 * Some sounds will have alternate inside version (for the case where the sound is being played in the same object the listener is in,
 * like if a missile fires and the listener is inside the sub firing the missile) in a subfolder of assets that have identical names.
 * You don't need to worry about that when doing stuff with Sound, since the sound engine figures out if it needs to play the outside or inside version.
 * @author Kyle
 *
 */
public class Sound {
	private String soundName;
	/**
	 * see the class documentation
	 * 
	 */
	public Sound(String soundName) {
		this.soundName = soundName;
	}

	/**
	 * 
	 * @param whether to get the internal or external version of the sound
	 * @return uses the singleton buffermanager to get the Buffer (i,e. the audio data for the sound)
	 * based on the soundName.
	 * @throws IOException if error loading the buffer from the sound's file
	 */
	public Buffer getBuffer(boolean internal) throws IOException {
		if (internal) {
			return BufferManager.getInstance().getSoundInternalVersion(soundName);
		} else {
			return BufferManager.getInstance().getSoundExternalVersion(soundName);
		}
	}
	
}
