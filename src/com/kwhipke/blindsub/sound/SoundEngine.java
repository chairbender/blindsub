package com.kwhipke.blindsub.sound;

import android.util.Log;
import com.kwhipke.blindsub.physics.VelocityVector;
import com.kwhipke.blindsub.submarine.SoundPhysObj;
import org.pielot.openal.*;

import com.kwhipke.blindsub.engine.Ticker;
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
public class SoundEngine implements Ticker, SoundEngineController {
	private PhysicsEngine physicsEngine;
	private PhysObj listener;
	private Map<PhysObj,SoundSource> objectSounds;
	private Set<PlaySoundDelayed> delayedSounds;
	
	/**
	 * 
	 * @param physicsEngine the physics engine that will let the sound engine know how the sounds will be heard
	 */
	public SoundEngine(PhysicsEngine physicsEngine) {
		this.physicsEngine = physicsEngine;
		this.objectSounds = new HashMap<PhysObj,SoundSource>();
        this.delayedSounds = new HashSet<PlaySoundDelayed>();
		physicsEngine.setPostTickEvent(this);
	}

    /**
     *
     * @param listener the physobj that represents the listener's position and orientation
     */
    public void setListener(PhysObj listener) {
        this.listener = listener;
    }
	
	/**
	 * Allows the sound engine to update positions of everything and trigger and delayed sounds
	 * @throws IOException 
	 */
	public void tick() throws IOException {
		//Update listener position
		Position sourcePosition = physicsEngine.getPositionOfPhysObj(listener);
		SoundEnv.getInstance().setListenerPos(sourcePosition.x, sourcePosition.y, 0);
        VelocityVector vec = listener.getVelocityVector();
        SoundEnv.getInstance().setListenerOrientation(listener.getVelocityVector().heading());
		
		//Update positions of soundsets based on the physicalobjects they represent
        //Remove any that are missing
        updateTrackedPhysicalObjects();

		
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

    /**
     * Updates the sounds to match the positions of the objects they are occurring on.
     * Removes any objects that no longer exist.
     */
    private void updateTrackedPhysicalObjects() {
        Set<PhysObj> toRemove = new HashSet<PhysObj>();
        for (PhysObj physicalObject : objectSounds.keySet()) {
            SoundSource soundSource = objectSounds.get(physicalObject);
            Position objectPosition = physicsEngine.getPositionOfPhysObj(physicalObject);
            if (objectPosition != null) {
                soundSource.updatePosition(objectPosition);
            } else {
                toRemove.add(physicalObject);
            }
        }
        for (PhysObj obj : toRemove) {
            objectSounds.remove(obj);
        }
    }

    public void playSound(Sound toPlay,PhysObj source, boolean loop) {
		Position sourcePosition = physicsEngine.getPositionOfPhysObj(source);
		SoundSource currentSounds = objectSounds.get(source);
		if (currentSounds == null) {
			currentSounds = new SoundSource(sourcePosition,source == listener);
			objectSounds.put(source, currentSounds);
		}
        try {
            currentSounds.play(toPlay,loop);
        } catch (IOException e) {
            Log.e("SoundEngine", e.getMessage());
        }
    }

    /**
     * Make a sound play in a certain amount of time
     * @param toPlay sound to play
     */
    private void playSoundDelayed(PlaySoundDelayed toPlay) {
        delayedSounds.add(toPlay);
    }

	
	/**
	 * Causes a ping to echo from the source and be echoed back to the source from any other physobjects in the engine.
	 * Not sure how I want this to work, but for now the response pings will be delayed based on how far the object is away. It might be
	 * better to just change its pitch. But the listener needs some idea of how far an object is, not just the angle of it relative to their heading. 
	 * @param toPlay sound to use as the ping sound
     * @param source what is emitting the sound
	 */
	public void doPing(Sound toPlay,
			PhysObj source) throws IOException {
        //If it's the listener doing the ping, play the internal sound immediately, then play the external sound delayed based on the positions of all other physical objects
        if (source == this.listener) {
            playSound(toPlay,source,false);
            for (PhysObj echoObject : physicsEngine.getEchoObjects(source)) {
                playSoundDelayed(new PlaySoundDelayed(echoObject,toPlay,physicsEngine.getTravelTime(source,echoObject)));
            }
        } else {
            //Play the sound only hearing it from the other, delayed
            playSoundDelayed(new PlaySoundDelayed(source,toPlay,physicsEngine.getTravelTime(source,listener)));
        }
		
	}

    @Override
    public void stopSound(Sound toStop, PhysObj physObj) {
        SoundSource toStopSource = objectSounds.get(physObj);
        toStopSource.stop(toStop);
    }
}
