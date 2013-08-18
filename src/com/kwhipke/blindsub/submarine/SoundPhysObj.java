package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.sound.Sound;
import com.kwhipke.blindsub.sound.SoundEngineController;

import java.io.IOException;

/**
 * A physical object that makes sounds
 * User: Kyle
 * Date: 8/16/13
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SoundPhysObj implements PhysObj {
    SoundEngineController soundEngineController;

    public SoundPhysObj(SoundEngineController soundEngineController) {
        this.soundEngineController = soundEngineController;
    }

    /**
     * Cause this object to start playing the sound
     * @param engineRunning sound to emit
     * @param b whether to loop the sound
     */
    protected void emitSound(Sound engineRunning, boolean b) throws IOException {
        soundEngineController.playSound(engineRunning,this,b);
    }

    /**
     *
     * @param toStop stop the sound if it is currently looping
     */
    protected void stopSound(Sound toStop) {
        soundEngineController.stopSound(toStop,this);
    }
}
