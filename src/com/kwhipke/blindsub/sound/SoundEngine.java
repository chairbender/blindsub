package com.kwhipke.blindsub.sound;

import org.pielot.openal.*;

import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.physics.PhysicsEngine;
import com.kwhipke.blindsub.physics.Position;

import android.app.Activity;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
/**
 * Manages audio.
 * @author Kyle
 *
 */
public class SoundEngine {
	private PhysicsEngine physicsEngine;
	private PhysObj listener;
	private Map<PhysObj,SoundSource> objectSounds;
	private Set<PlaySoundDelayed> delayedSounds;
	
	/**
	 * 
	 * @param physicsEngine the physics engine that will let the sound engine know how the sounds will be heard
	 * @param listener the physobj who represents the listener
	 */
	public SoundEngine(PhysicsEngine physicsEngine,PhysObj listener) {
		this.physicsEngine = physicsEngine;
		this.listener = listener;
		this.objectSounds = new HashMap<PhysObj,SoundSource>();
	}
	
	/**
	 * Allows the sound engine to update positions of everything and trigger and delayed sounds
	 * @throws IOException 
	 */
	//TODO: Fire off this tick somewhere. It should probably happen in the same thread as the physics engine to keep things simple. So probably
	// make the sound engine subscribe to the tick method of the physics engine.
	public void tick() throws IOException {
		//Update listener position
		Position sourcePosition = physicsEngine.getPositionOfPhysObj(listener);
		SoundEnv.getInstance().setListenerPos(sourcePosition.x, sourcePosition.y, 0);
		
		//Update positions of soundsets based on the physicalobjects they represent
		for (PhysObj physicalObject : objectSounds.keySet()) {
			SoundSource soundSource = objectSounds.get(physicalObject);
			soundSource.updatePosition(physicsEngine.getPositionOfPhysObj(physicalObject));
		}
		
		//Play delayed sounds if it's time
		Set<PlaySoundDelayed> toRemove = new HashSet<PlaySoundDelayed>();
		for (PlaySoundDelayed delayedSound : delayedSounds) {
			if (delayedSound.playIfReady(this)) {
				toRemove.add(delayedSound);
			}
		}
		for (PlaySoundDelayed soundToRemove : toRemove) {
			delayedSounds.remove(soundToRemove);
		}
		
	}
	
	//TODO: Change what is passed in so user can pass in subclasses of sound to play things like engine start, run, then stop, and looped sounds
	public void playSound(Sound toPlay,PhysObj source) throws IOException {

		Position sourcePosition = physicsEngine.getPositionOfPhysObj(source);
		SoundSource currentSounds = objectSounds.get(source);
		if (currentSounds == null) {
			currentSounds = new SoundSource(sourcePosition,source == listener);
			objectSounds.put(source, currentSounds);
		}
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
		//TODO: Implement
		
	}
}
