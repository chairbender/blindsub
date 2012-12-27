package com.kwhipke.blindsub.submarine;

import java.io.IOException;

import org.pielot.openal.*;

import com.kwhipke.blindsub.GameMap;
import com.kwhipke.blindsub.OnPingListener;
import com.kwhipke.blindsub.Pausable;
import com.kwhipke.blindsub.PhysObj;
import com.kwhipke.blindsub.SoundManager;
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
 * Handles all the logic for the submarine,
 * including playing sound.
 * @author Kyle
 *
 */
public class PlayerSubmarine extends Submarine implements Pausable {
	
	private final static String    TAG    = "Submarine";

    private SoundEnv env;

    private Source ping;
    private Source engineStart;
    private Source engineRun;
    private Source engineStop;
    private Source torpedoLaunch;
    private Source bubbles;
	
	Context context;
	
	
	/*state related stuff*/
	private SubState state = SubState.STOPPED;
	private enum SubState {
		STOPPED,
		DRIVING
	};
	

	
	//Current sensor data
	SensorManager sensorManager;
	private float lastOrientation[] = new float[3];
	private float lastRotationMatrix[] = new float[9];
	private float lastGravityVector[] = new float[3];
	private float lastGeoVector[] = new float[3];
	
	private double x, y; //0,0 is the bottom left of the map
	private double heading; //0 is east
	
	//Sensor event listeners
	SensorEventListener gravityListener;
	SensorEventListener geoListener;
	
	//Ping listeners
	OnPingListener pingListener;
	
	//Stats in meters (our atomic physics unit) per second and degrees
	private static final double METERS_PER_SECOND = 1.0;
	private static final double MAX_DEGREES_PER_SECOND = 30; //max turning rate

	private static final double COLLISION_RADIUS = 5.0;

	private int orientation = 0;

	private long startupSoundLengthInMillis;
	
	private GameMap currentMap;
	

    //Pass in the activity it is running in as parentActivity
	public PlayerSubmarine(Activity parentActivity, GameMap currentMap, float startX, float startY, float startHeading) {
		super(parentActivity, currentMap);
		this.x = startX;
		this.y = startY;
		this.heading = startHeading;
		this.currentMap = currentMap; //TODO: This is duplicated
		
		//Get durations before anything else otherwise audio stuff will crash
		try {
			startupSoundLengthInMillis = AudioUtil.getSoundDuration(parentActivity, "startup");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.context = context;
		/* First we obtain the instance of the sound environment. */
        this.env = SoundEnv.getInstance(parentActivity);
        env.setListenerOrientation(AudioUtil.getOpenAlOrientation(heading));
        //Get the sound manager
        SoundManager manager = SoundManager.getSoundManager(parentActivity);
        /*
         * To actually play a sound and place it somewhere in the sound
         * environment, we have to create sources. Each source has its own
         * parameters, such as 3D position or pitch. Several sources can
         * share a single buffer.
         */
		try {
			this.engineStart = env.addSource(manager.getSound("startup"));
			this.engineRun = env.addSource(manager.getSound("run"));
			this.engineStop = env.addSource(manager.getSound("stop"));
	        this.ping = env.addSource(manager.getSound("ping"));
	        this.torpedoLaunch = env.addSource(manager.getSound("torpedofire"));
	        this.bubbles = env.addSource(manager.getSound("bubbles"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //Register the handlers for getting sensor data
        
        //define our listeners
        gravityListener = new SensorEventListener() {
        	@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSensorChanged(SensorEvent arg0) {
				lastGravityVector = arg0.values;
			}
        };
        
        geoListener = new SensorEventListener() {
        	@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSensorChanged(SensorEvent arg0) {
				lastGeoVector = arg0.values;
			}
        };
        
        sensorManager = (SensorManager)parentActivity.getSystemService(parentActivity.SENSOR_SERVICE);
        this.onResume();
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
	
	@Override
	public void onPause() {
		sensorManager.unregisterListener(gravityListener);
		sensorManager.unregisterListener(geoListener);
	}
	
	@Override
	public void onResume() {
		Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor geoSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(gravityListener, gravitySensor,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(geoListener, geoSensor,SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void setOnPingListener(OnPingListener toAdd) {
		this.pingListener = toAdd;
	}

	public double[] getPosition() {
		double result[] = new double[2];
		result[0] = this.x;
		result[1] = this.y;
		return result;
	}
	
	//gets the heading in 360 degrees with 0 being east
	public double getOrientation() {
		return heading;
	}

	@Override
	public boolean resolveCollision(PhysObj other) {
		return this.resolveCollision(other, METERS_PER_SECOND, heading);
		
	}

	@Override
	protected void setX(double x) {
		this.x = x;
		
	}

	@Override
	protected void setY(double y) {
		this.y = y;
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
}
