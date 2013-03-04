package com.kwhipke.blindsub.submarine;

import java.io.IOException;

import org.pielot.openal.*;

import com.kwhipke.blindsub.GameEngine;
import com.kwhipke.blindsub.OnPingListener;
import com.kwhipke.blindsub.Pausable;
import com.kwhipke.blindsub.physics.CollisionBounds;
import com.kwhipke.blindsub.physics.PhysObj;
import com.kwhipke.blindsub.physics.Position;
import com.kwhipke.blindsub.physics.VelocityVector;
import com.kwhipke.blindsub.sound.SoundEngine;
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
public class ControlledSubmarine extends Submarine implements OnThrottleChanged, OnButtonChanged, OnSteeringChanged{
    private Source ping;
    private Source engineStart;
    private Source engineRun;
    private Source engineStop;
    private Source torpedoLaunch;
    private Source bubbles;
	
	//Post refactor
	SubmarineSpatialState submarineState;
	SubmarineStatus submarineStatus;

	public ControlledSubmarine(SubmarineState initialState, SoundEngine soundEngine, SubmarineType submarineType) {
		super(initialState, soundEngine, submarineType);
		this.submarineStatus = new SubmarineStatus();
	}
	
	/**
	 * Start the sub moving
	 */
	public void drive() {
		if (state != SubState.DRIVING) {
			//Play the engine start sound and follow it with the engine sustain sound loop
			state = SubState.DRIVING;
			engineStart.play(false);
			//Have some overlap between the start and and run loop
			new Handler().postDelayed(new Runnable() {
				public void run() {
					if (state == SubState.DRIVING) {
						engineRun.play(true);
					}
				}
			}, startupSoundLengthInMillis - 200);
			
			new Handler().postDelayed(new Runnable() {
				public void run() {
					if (state == SubState.DRIVING) {
						engineStart.stop();
					}
				}
			}, startupSoundLengthInMillis);
			//play some moving through water sounds
			new Handler().postDelayed(new Runnable() {
				public void run() {
					if (state == SubState.DRIVING) {
						bubbles.play(true);
					}
				}
			}, startupSoundLengthInMillis + 500);
		}
	}
	
	/**
	 * Stop the sub
	 */
	public void stop() {
		if (state != SubState.STOPPED) {
			//play the engine stop sound and stop the engine start sound
			state = SubState.STOPPED;
			engineStart.stop();
			engineRun.stop();
			engineStop.play(false);
			bubbles.stop();
		}
	}
	
	public void ping() {
		this.ping.play(false);
		if (pingListener != null) {
			this.pingListener.onPing(this.x, this.y);
		}
		Log.i(TAG,"x, y: " + this.x + ", " + this.y);
	}
	
	public void fire() {
		this.torpedoLaunch.play(false);
		currentMap.addBullet(new Torpedo(this.x,this.y,this.heading,this));
	}

	@Override
	public void tick(long elapsedMillis) {
		if (state == SubState.DRIVING) {
			double elapsedSeconds = elapsedMillis / 1000.0;
			float roll = 0;
			//Compute heading
			if (SensorManager.getRotationMatrix(lastRotationMatrix, null,
                   lastGravityVector, lastGeoVector)) {
				SensorManager.getOrientation(lastRotationMatrix, lastOrientation);
				
				//Convert the tilt to degrees
				roll = lastOrientation[1] * 57.2957795f;
				//0 to 90 is turning left, 0 to -90 is turning right
				if (roll > 0) {
					roll = Math.min(roll, 70);
					heading += this.MAX_DEGREES_PER_SECOND * (roll / 70.0) * elapsedSeconds;
				} else {
					roll = Math.max(roll, -70);
					heading -= this.MAX_DEGREES_PER_SECOND * (roll / -70.0) * elapsedSeconds; 
				}
				
				//loop heading around so it's always between 0 and 360
				if (heading >= 360.0) {
					heading = heading - 360;
				} else if (heading < 0) {
					heading = heading + 360;
				}
				
				//Update its orientation
				env.setListenerOrientation(AudioUtil.getOpenAlOrientation(heading));
			}

			//Calculate the horizontal and vertical components of the velocity vector
			double components[] = PhysicsUtil.getVelocityComponents(heading,METERS_PER_SECOND);
			this.x = this.x + components[0]*elapsedSeconds;
			this.y = this.y + components[1]*elapsedSeconds;
		}
	}

	public double[] getPosition() {
		double result[] = new double[2];
		result[0] = this.x;
		result[1] = this.y;
		return result;
	}

	@Override
	protected boolean resolveBulletHit(Bullet bullet) {
		//Can't be hit by own bullets
		if (bullet.getCreator() != this) {
			//Play a mechanical hit sound and do some damage to this
			//Play the hit based on the side of the sub it hit
			
			//Do damage
			this.setHealth(this.getHealth() - bullet.getDamage());
			
			double x = bullet.getPosition()[0] - this.getPosition()[0];
			double y = bullet.getPosition()[1] - this.getPosition()[1];
			
			hit.setPosition((float)x, (float)y, 0f);
			hit.play(false);
			
			//if dead now, return false.
			if (this.getHealth() <= 0) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void onSteeringChanged(Steering newSteering) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onButtonPressed(SubmarineButton whichButton) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public VelocityVector getVelocityVector() {
		return new VelocityVector(submarineType.getTopSpeed().throttledBy(submarineStatus.getThrottle()),submarineState.getHeading());
	}

	@Override
	public boolean doCollision(PhysObj other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CollisionBounds getCollisionBounds() {
		return submarineType.getCollisionBounds();
	}	
}
