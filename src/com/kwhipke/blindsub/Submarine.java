package com.kwhipke.blindsub;

import java.io.IOException;

import org.pielot.openal.*;

import com.kwhipke.blindsub.util.AudioUtil;

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
 * including playing sound
 * @author Kyle
 *
 */
//TODO: Eliminate lag. Use this https://github.com/AerialX/openal-soft-android/commit/5fae12e4ab0ebf0781a0a7a6d96a10e19008dc23 or at least that project instead
public class Submarine implements PhysObj {
	
	private final static String    TAG    = "HelloOpenAL4Android";

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
	
	private double x, y;    // 0, 0 is the bottom left of the map
	private double heading; //0 is east 90 is north. in degrees
	
	//Current sensor data
	SensorManager sensorManager;
	private float lastOrientation[] = new float[3];
	private float lastRotationMatrix[] = new float[9];
	private float lastGravityVector[] = new float[3];
	private float lastGeoVector[] = new float[3];
	
	//Sensor event listeners
	SensorEventListener gravityListener;
	SensorEventListener geoListener;
	
	//Stats in meters (our atomic physics unit) per second and degrees
	private static final double METERS_PER_SECOND = 1.0;
	private static final double MAX_DEGREES_PER_SECOND = 30; //max turning rate

	private int orientation = 0;

	private long startupSoundLengthInMillis;
	

    //Pass in the activity it is running in as parentActivity
	public Submarine(Activity parentActivity, float startX, float startY, float startHeading) {
		this.x = startX;
		this.y = startY;
		this.heading = startHeading;
		
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

        Buffer ping = null;
        Buffer start = null;
        Buffer run = null;
        Buffer stop = null;
        Buffer torpedoLaunch = null;
        Buffer bubbles = null;
		try {
			ping = env.addBuffer("ping");
			start = env.addBuffer("startup");
			run = env.addBuffer("run");
			stop = env.addBuffer("stop");
			torpedoLaunch = env.addBuffer("torpedofire");
			bubbles = env.addBuffer("bubbles");
		} catch (IOException e) {
			e.printStackTrace();
		}

        /*
         * To actually play a sound and place it somewhere in the sound
         * environment, we have to create sources. Each source has its own
         * parameters, such as 3D position or pitch. Several sources can
         * share a single buffer.
         */
		this.engineStart = env.addSource(start);
		this.engineRun = env.addSource(run);
		this.engineStop = env.addSource(stop);
        this.ping = env.addSource(ping);
        this.torpedoLaunch = env.addSource(torpedoLaunch);
        this.bubbles = env.addSource(bubbles);

        /*
         * These sounds are perceived from the perspective of a virtual
         * listener. Initially the position of this listener is 0,0,0. The
         * position and the orientation of the virtual listener can be
         * adjusted via the SoundEnv class.
         */
        this.env.setListenerOrientation(0);
        
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
	void drive() {
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
	void stop() {
		if (state != SubState.STOPPED) {
			//play the engine stop sound and stop the engine start sound
			state = SubState.STOPPED;
			engineStart.stop();
			engineRun.stop();
			engineStop.play(false);
			bubbles.stop();
		}
	}
	
	void ping() {
		this.ping.play(false);
		orientation += 10;
		this.env.setListenerOrientation(orientation);
	}
	
	void fire() {
		this.torpedoLaunch.play(false);
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
			}

			//Calculate the horizontal and vertical components of the velocity vector
			double components[] = getVelocityComponents();
			this.x = this.x + components[0]*elapsedSeconds;
			this.y = this.y + components[0]*elapsedSeconds;
			
			Log.i("Submarine", "heading: " + heading + " degrees");
			Log.i("Submarine", "x, y: " + this.x + " " + this.y);
			Log.i("Submarine", "roll: " + roll);
		}
	}
	
	//Returns the x and y components of the velocity vector basd on the
	//current heading and the velocity (x is the first and y is the second
	private double[] getVelocityComponents() {
		double result[] = new double[2];
		double headingRadians = heading * Math.PI / 180;
		
		if (headingRadians >= 0 && headingRadians < 90) {
			result[0] = Math.sin(headingRadians) * METERS_PER_SECOND;
			result[1] = Math.cos(headingRadians) * METERS_PER_SECOND;
		} else if (headingRadians >= 90 && headingRadians < 180) {
			result[0] = -1 * Math.sin(headingRadians) * METERS_PER_SECOND;
			result[1] = -1 * Math.cos(headingRadians) * METERS_PER_SECOND;
		} else if (headingRadians >= 180 && headingRadians < 270) {
			result[0] = Math.sin(headingRadians) * METERS_PER_SECOND;
			result[1] = Math.cos(headingRadians) * METERS_PER_SECOND;
		} else {
			result[0] = -1 * Math.sin(headingRadians) * METERS_PER_SECOND;
			result[1] = -1 * Math.cos(headingRadians) * METERS_PER_SECOND;
		}
		
		return result;
	}
	
	public void onPause() {
		sensorManager.unregisterListener(gravityListener);
		sensorManager.unregisterListener(geoListener);
	}
	
	public void onResume() {
		Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor geoSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(gravityListener, gravitySensor,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(geoListener, geoSensor,SensorManager.SENSOR_DELAY_GAME);
	}
	
	
	
}
