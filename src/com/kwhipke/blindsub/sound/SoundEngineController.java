package com.kwhipke.blindsub.sound;

import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.submarine.ControlledSubmarine;
import com.kwhipke.blindsub.submarine.SoundPhysObj;

import java.io.IOException;

/**
 * Provides a control for objects to interact with the soundengine in a safe way.
 * User: Kyle
 * Date: 8/16/13
 * Time: 10:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SoundEngineController {

    /**
     * Causes the sound to be played from the specified source, looped if desired. If toPlay is already looping
     * and looping is specified, no change is made. If toPlay is already being played and loop is false, though, the sound
     * will still play. No need to return a handle because the physobj takes care of updating the sound position, and
     * the stopSound method will work since there is ever only one looping instance of a given sound.
     */
    public void playSound(Sound toPlay, PhysObj source, boolean loop);

    /**
     * Causes a ping to echo from the source and be echoed back to the source from any other physobjects in the engine.
     * Not sure how I want this to work, but for now the response pings will be delayed based on how far the object is away. It might be
     * better to just change its pitch. But the listener needs some idea of how far an object is, not just the angle of it relative to their heading.
     * @param toPlay sound to use as the ping sound
     * @param source what is emitting the sound
     */
    public void doPing(Sound toPlay, PhysObj source) throws IOException;

    /**
     * stops the physobject from emitting the sound if it is currently looping
     * @param toStop sound to stop
     * @param soundPhysObj physobj whose sound should be stopped
     */
    void stopSound(Sound toStop, PhysObj soundPhysObj);
}
