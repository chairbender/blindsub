package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.physics.Heading;
import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.physics.PhysicsEngineController;
import com.kwhipke.blindsub.physics.VelocityVector;
import com.kwhipke.blindsub.sound.SoundEngineController;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.submarine.type.SubmarineType;
import com.kwhipke.blindsub.units.Meters;

/**
 * A submarine that just sits there
 * User: Kyle
 * Date: 8/18/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Rock extends Submarine{
    public static final VelocityVector VECTOR = new VelocityVector(new Speed(new Meters(0)),new Heading(0));

    public Rock(SubmarineState initialState, SubmarineType submarineType, SoundEngineController soundEngineController) {
        super(initialState, submarineType, soundEngineController);
    }

    @Override
    public VelocityVector getVelocityVector() {
        return VECTOR;
    }

    @Override
    public void tick(long elapsedMilliseconds, PhysicsEngineController physicsEngineController) {
        //don't do nothin
    }

    @Override
    public boolean collidesWith(PhysObj other) {
        return true;
    }
}
