package com.kwhipke.blindsub.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.pielot.openal.Buffer;
import org.pielot.openal.SoundEnv;

/**
 * Manages loading audio buffers. There's only one instance of this for the whole project.
 * @author Kyle
 *
 */
class BufferManager {
	//Never need to have more than one
	private static BufferManager bufferManager;
	
	private Map<String, Buffer> buffers;
	SoundEnv env;
	
	/**
	 * 
	 * @param env the sound environment that the buffers will be loaded in
	 */
	private BufferManager() {
		buffers = new HashMap<String,Buffer>();
		env = SoundEnv.getInstance();
	}
	
	/**
	 * 
	 * @return the buffer manager
	 */
	public static BufferManager getInstance() {
		if (bufferManager == null) {
			return new BufferManager();
		} else {
			return bufferManager;
		}
	}
	/**
	 * 
	 * @param name name of the wav file in the assets folder for this sound
	 * example: "ping" would get the buffer for "assets/ping.wav"
	 * @return a buffer for the sound. DO NOT MODIFY THIS BUFFER as it
	 * it may be used accross multiple objects.
	 * @throws IOException if no sound found
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
