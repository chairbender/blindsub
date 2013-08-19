package com.kwhipke.blindsub.submarine;

import android.util.Log;
import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.physics.PhysicsEngineController;
import com.kwhipke.blindsub.physics.VelocityVector;
import com.kwhipke.blindsub.sound.SoundEngineController;
import com.kwhipke.blindsub.sound.submarine.SubmarineSounds;
import com.kwhipke.blindsub.submarine.control.Steering;
import com.kwhipke.blindsub.submarine.control.SubmarineButton;
import com.kwhipke.blindsub.submarine.control.SubmarineButtonType;
import com.kwhipke.blindsub.submarine.control.ThrottlePosition;
import com.kwhipke.blindsub.submarine.control.event.OnButtonChanged;
import com.kwhipke.blindsub.submarine.control.event.OnSteeringChanged;
import com.kwhipke.blindsub.submarine.control.event.OnThrottleChanged;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.type.SubmarineType;

import java.io.IOException;

/**
 * A submarine that can be controlled by some controller.
 * @author Kyle
 *
 */
public class ControlledSubmarine extends ShootingSubmarine implements OnThrottleChanged, OnButtonChanged, OnSteeringChanged{

	public ControlledSubmarine(SubmarineState initialState, SubmarineType submarineType,SoundEngineController soundEngineController) {
		super(initialState, submarineType,soundEngineController);
	}

	@Override
	public void tick(long elapsedMillis,PhysicsEngineController controller) {
        super.tick(elapsedMillis,controller);
		//Update the state
		currentState.tick(submarineType,elapsedMillis);
	}

    @Override
    public boolean collidesWith(PhysObj other) {
        return !(other instanceof Torpedo && ((Torpedo)other).wasCreatedBy(this));
    }

    @Override
	public void onSteeringChanged(Steering newSteering) {
		currentState.setSteering(newSteering);
	}

	@Override
	public void onButtonPressed(SubmarineButton whichButton) {
        try{
            if (whichButton.getButtonType().equals(SubmarineButtonType.FIRE)) {
                soundEngineController.playSound(SubmarineSounds.TORPEDO_FIRING, this,false);
                this.fire();
            } else if (whichButton.getButtonType().equals(SubmarineButtonType.PING)) {
                soundEngineController.doPing(SubmarineSounds.TORPEDO_PING, this);
            }
        } catch(Exception ex) {
            Log.e("ControlledSubmarine",ex.getMessage());
        }
	}

	@Override
	public void onButtonHeld(SubmarineButton whichButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onButtonReleased(SubmarineButton whichButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onThrottleChanged(ThrottlePosition newThrottle) {
        try {
            if (!newThrottle.isZero()) {
                emitSound(SubmarineSounds.ENGINE_RUNNING, true);
            } else {
                stopSound(SubmarineSounds.ENGINE_RUNNING);
            }
            currentState.changeThrottle(newThrottle);
        } catch (Exception ex) {
            Log.e("ControlledSubmarine",ex.getMessage());
        }
	}



    @Override
	public VelocityVector getVelocityVector() {
		return new VelocityVector(submarineType.getTopSpeed().throttledBy(currentState.getCurrentThrottle()),currentState.getCurrentHeading());
	}

}
