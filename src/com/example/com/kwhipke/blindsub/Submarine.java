package com.example.com.kwhipke.blindsub;

import java.io.IOException;

import org.pielot.openal.*;

import com.example.com.kwhipke.blindsub.util.AudioUtil;

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
public class Submarine {
	
	private final static String    TAG    = "HelloOpenAL4Android";

    private SoundEnv            env;

    private Source                ping;
	
	Context context;
	
	
	/*state related stuff*/
	private SubState state = SubState.STOPPED;
	private enum SubState {
		STOPPED,
		DRIVING
	};
	
	
	/*Sound related fields*/
	SoundPool soundPool; //manages playing all the sounds
	
	int startEngineId;
    int stopEngineId;
    int runEngineId;
    int pingId; 
    
    int startEngineStreamId;
    int stopEngineStreamId;
    int runEngineStreamId;

	private int orientation = 0;

    //Pass in the activity it is running in as parentActivity
	public Submarine(Activity parentActivity) {
		this.context = context;
		/* First we obtain the instance of the sound environment. */
        this.env = SoundEnv.getInstance(parentActivity);

        /*
         * Now we load the sounds into the memory that we want to play
         * later. Each sound has to be buffered once only. To add new sound
         * copy them into the assets folder of the Android project.
         * Currently only mono .wav files are supported.
         */
        Buffer ping = null;
		try {
			ping = env.addBuffer("ping");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        /*
         * To actually play a sound and place it somewhere in the sound
         * environment, we have to create sources. Each source has its own
         * parameters, such as 3D position or pitch. Several sources can
         * share a single buffer.
         */
        this.ping = env.addSource(ping);

        // Now we spread the sounds throughout the sound room.
        this.ping.setPosition(6, 0, -12);

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
//		if (state != SubState.DRIVING) {
//			//Play the engine start sound and follow it with the engine sustain sound loop
//			state = SubState.DRIVING;
//			startEngineStreamId = soundPool.play(startEngineId, .2f, .2f, 1, 0, 1.0f);
//			new Handler().postDelayed(new Runnable() {
//				public void run() {
//					if (state == SubState.DRIVING) {
//						soundPool.stop(startEngineStreamId);
//						runEngineStreamId = soundPool.play(runEngineId, .2f, .2f, 1, -1, 1.0f);
//					}
//				}
//			}, AudioUtil.getSoundDuration(context, R.raw.startup));
//		}
	}
	
	/**
	 * Stop the sub
	 */
	void stop() {
//		if (state != SubState.STOPPED) {
//			//play the engine stop sound and stop the engine start sound
//			state = SubState.STOPPED;
//			soundPool.stop(startEngineStreamId);
//			soundPool.stop(runEngineStreamId);
//			stopEngineStreamId = soundPool.play(stopEngineId, .2f, .2f, 1, 0, 1.0f);
//		}
	}
	
	void ping() {
		this.ping.play(false);
		orientation += 10;
		this.env.setListenerOrientation(orientation);
	}
	
}
