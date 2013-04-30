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
	private PhysicsEngine physicsEngine;
	private PhysObj listener;
	private Map<PhysObj,SoundSet> objectSounds;
	
	/**
	 * 
	 * @param physicsEngine the physics engine that will let the sound engine know how the sounds will be heard
	 * @param listener the physobj who represents the listener
	 */
	public SoundEngine(PhysicsEngine physicsEngine,PhysObj listener) {
		this.physicsEngine = physicsEngine;
		this.listener = listener;
		this.objectSounds = new HashMap<PhysObj,SoundSet>();
	}
	
	//TODO: Change what is passed in so user can pass in subclasses of sound to play things like engine start, run, then stop, and looped sounds
	public void playSound(Sound toPlay,PhysObj source) throws IOException {
		//get the position to play the sound at from the physics engine, based on the
		//listener's position
		//TODO: Finish implementing this
		if (source == listener) {
			//TODO: play the indoor sound
		}//TODO: else
		Position sourcePosition = physicsEngine.getPositionOfPhysObj(source);
		SoundSet currentSounds = objectSounds.get(source);
		
		if (currentSounds == null) {
			currentSounds = new SoundSet(sourcePosition);
			objectSounds.put(source, currentSounds);
		} 
		//TODO: should probably invert this so you call play on sounds and pass in the sound engine
		currentSounds.play(toPlay);
		
		
	}

	
	/**
	 * Causes a ping to echo from the source and be echoed back to the source from any other physobjects in the engine.
	 * Not sure how I want this to work, but for now the response pings will be delayed based on how far the object is away. It might be
	 * better to just change its pitch. But the listener needs some idea of how far an object is, not just the angle of it relative to their heading. 
	 * @param torpedoPing
	 * @param controlledSubmarine
	 */
	public void doPing(Sound torpedoPing,
			PhysObj source) {
		// TODO: implement the pinging behavior
		
	}
}
