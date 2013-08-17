package com.kwhipke.blindsub.sound;

/**
 * Represents something that can make use of the sound engine
 * User: Kyle
 * Date: 8/16/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SoundPlayer {

    /**
     * Allows the object to perform whatever sound-related stuff it wants
     * @param soundEngineCOntroller a controller for this object to interact with the sound engine
     */
    public void playSound(SoundEngineController soundEngineCOntroller);
}
