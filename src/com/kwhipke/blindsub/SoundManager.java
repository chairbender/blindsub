package com.kwhipke.blindsub;

import org.pielot.openal.*;

import android.app.Activity;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
/**
 * Manages audio buffers. Singleton
 * @author Kyle
 *
 */
public class SoundManager {
	private Map<String, Buffer> buffers;
	private SoundEnv env;
	
	private static SoundManager soundManager;
	
	private SoundManager(Activity parentActivity) {
		buffers = new HashMap<String,Buffer>();
		env = SoundEnv.getInstance(parentActivity);
	}
	
	public static SoundManager getSoundManager(Activity parentActivity) {
		if (soundManager == null)
			soundManager = new SoundManager(parentActivity);
		return soundManager;
	}
	
	/**
	 * 
	 * @param name name of the wav file in the assets folder for this sound
	 * example: "ping" would get the buffer for "assets/ping.wav"
	 * @return a buffer for the sound. DO NOT MODIFY THIS BUFFER as it
	 * it may be used accross multiple objects.
	 * @throws IOException if sound not found
	 */
	public Buffer getSound(String name) throws IOException {
		if (buffers.get(name) != null) {
			return buffers.get(name);
		} else {
			buffers.put(name,env.addBuffer(name));
			return buffers.get(name);
		}
	}
	
	
}
