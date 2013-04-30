package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.submarine.stats.TurningRadius;
import java.io.IOException;

import org.pielot.openal.*;

import com.kwhipke.blindsub.GameEngine;
import com.kwhipke.blindsub.OnPingListener;
import com.kwhipke.blindsub.Pausable;
import com.kwhipke.blindsub.physics.CollisionBounds;
import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.physics.PhysicsEngine;
import com.kwhipke.blindsub.physics.Position;
import com.kwhipke.blindsub.physics.VelocityVector;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.sound.submarine.SubmarineSounds;
import com.kwhipke.blindsub.submarine.control.*;
import com.kwhipke.blindsub.submarine.control.event.OnButtonChanged;
import com.kwhipke.blindsub.submarine.control.event.OnSteeringChanged;
import com.kwhipke.blindsub.submarine.control.event.OnThrottleChanged;
import com.kwhipke.blindsub.submarine.state.SubmarineSpatialState;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.state.SubmarineStatus;
import com.kwhipke.blindsub.submarine.type.SubmarineType;
import com.kwhipke.blindsub.util.AudioUtil;
import com.kwhipke.blindsub.util.PhysicsUtil;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;

/**
 * A submarine that can be controlled by some controller.
 * @author Kyle
 *
 */
public class ControlledSubmarine extends ShootingSubmarine implements OnThrottleChanged, OnButtonChanged, OnSteeringChanged{

	public ControlledSubmarine(SubmarineState initialState, SoundEngine soundEngine, SubmarineType submarineType, PhysicsEngine containingPhysicsEngine) {
		super(initialState, soundEngine, submarineType,containingPhysicsEngine);
	}

	@Override
	public void tick(long elapsedMillis) {
		//Update the state
		currentState.tick(submarineType,elapsedMillis);
	}

	@Override
	public void onSteeringChanged(Steering newSteering) {
		currentState.setSteering(newSteering);
	}

	@Override
	public void onButtonPressed(SubmarineButton whichButton) throws IOException {
		if (whichButton.getButtonType().equals(SubmarineButtonType.FIRE)) {
			soundEngine.playSound(SubmarineSounds.TORPEDO_FIRING, this);
			this.fire();
		} else if (whichButton.getButtonType().equals(SubmarineButtonType.PING)) {
			soundEngine.doPing(SubmarineSounds.TORPEDO_PING, this);
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
		currentState.changeThrottle(newThrottle);
	}

	@Override
	public VelocityVector getVelocityVector() {
		return new VelocityVector(submarineType.getTopSpeed().throttledBy(currentState.getCurrentThrottle()),currentState.getCurrentHeading());
	}

}
