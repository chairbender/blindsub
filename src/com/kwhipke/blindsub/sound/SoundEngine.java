package com.kwhipke.blindsub.sound;

import org.pielot.openal.*;

import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.physics.PhysicsEngine;
import com.kwhipke.blindsub.physics.Position;

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
	private PhysicsEngine physicsEngine;
	
	public SoundEngine(PhysicsEngine physicsEngine) {
		soundEnv = SoundEnv.getInstance();
		buffers = new BufferManager(soundEnv);
		this.physicsEngine = physicsEngine;
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
	
	public void playSound(Sound toPlay,PhysObj source) {
		//get the position to play the sound at from the physics engine, based on the
		//listener's position
		//TODO: Finish implementing this
		Position sourcePosition = physicsEngine.getPositionOfPhysObj(source);
	}
}
