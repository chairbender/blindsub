package com.kwhipke.blindsub;

import java.io.IOException;

import org.pielot.openal.*;

import com.kwhipke.blindsub.util.AudioUtil;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

/**
 * Handles all the logic for the submarine,
 * including playing sound
 * @author Kyle
 *
 */
//TODO: Eliminate lag. Use this https://github.com/AerialX/openal-soft-android/commit/5fae12e4ab0ebf0781a0a7a6d96a10e19008dc23 or at least that project instead
public class Submarine implements PhysObj{
	
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
	
	private float x, y;
	private float heading;
	
	//Stats in meters (our atomic physics unit) per second
	private static final float SPEED = 1.0f;
	
	

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
		// TODO: Implement the physical movement of the submarine
		
	}
	
}
