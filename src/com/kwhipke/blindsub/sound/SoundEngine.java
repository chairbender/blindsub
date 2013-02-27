package com.kwhipke.blindsub.sound;

import org.pielot.openal.*;

import android.app.Activity;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
/**
 * Manages audio.
 * @author Kyle
 *
 */
public class SoundEngine {
	private BufferManager buffers;
	private SoundEnv soundEnv;
	
	public SoundEngine() {
		soundEnv = SoundEnv.getInstance();
		buffers = new BufferManager(soundEnv);
	}

	/**
	 * 
	 * @param string name of the audio file in the assets folder, without the ".wav"
	 * @return A source that can be used to control the position and playback of the sound.
	 * @throws IOException if sound is not found 
	 */
	public Source createSource(String string) throws IOException {
		return soundEnv.addSource(buffers.getSound(string)); 
		
	}
}
