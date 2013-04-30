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
	
	//buffers holding internal and external versions of sounds
	private Map<String, Buffer> internalSoundBuffers;
	private Map<String, Buffer> externalSoundBuffers;
	SoundEnv env;
	
	/**
	 * 
	 * @param env the sound environment that the buffers will be loaded in
	 */
	private BufferManager() {
		internalSoundBuffers = new HashMap<String,Buffer>();
		externalSoundBuffers = new HashMap<String,Buffer>();
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
	 * example: "ping" would get the buffer for "assets/internal/ping.wav"
	 * @return a buffer for the sound, which represents the sound as it would be heard if it originated from the listener. DO NOT MODIFY THIS BUFFER as it
	 * it may be used accross multiple objects.
	 * @throws IOException if no sound found
	 */
	public Buffer getSoundInternalVersion(String name) throws IOException {
		return getSound(name,true);
	}
	
	/**
	 * 
	 * @param name name of the wav file in the assets folder for this sound
	 * example: "ping" would get the buffer for "assets/external/ping.wav"
	 * @return a buffer for the sound, which represents the sound as it would be heard if it originated from somewhere other than the listener. DO NOT MODIFY THIS BUFFER as it
	 * it may be used accross multiple objects.
	 * @throws IOException if no sound found
	 */
	public Buffer getSoundExternalVersion(String name) throws IOException {
		return getSound(name,false);
	}
	
	/**
	 * 
	 * @param name name of the wav file. if internal, should be in assets/internal/<name>.wav, if notm will look in assets/external/<name>.wav
	 * @param whether it should look in the external or internal folder for the sound
	 * @return the sound
	 * @throws IOException 
	 */
	private Buffer getSound(String name, boolean isInternal) throws IOException {
		Map<String,Buffer> bufferToUse;
		String pathPrefix;
		if (isInternal) {
			bufferToUse = internalSoundBuffers;
			pathPrefix = "internal/";
		} else {
			bufferToUse = externalSoundBuffers;
			pathPrefix = "external/";
		}
		if (bufferToUse.get(name) != null) {
			return bufferToUse.get(name);
		} else {
			bufferToUse.put(name,env.addBuffer(pathPrefix + name));
			return bufferToUse.get(name);
		}
	}
}
