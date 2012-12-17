package com.example.com.kwhipke.blindsub;

import com.example.com.kwhipke.blindsub.util.AudioUtil;

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

	public Submarine(Context context) {
		this.context = context;
		
		soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        
        //Load our sounds
        AssetFileDescriptor startEngine = context.getResources().openRawResourceFd(R.raw.startup);
        AssetFileDescriptor runEngine = context.getResources().openRawResourceFd(R.raw.run);
        AssetFileDescriptor stopEngine = context.getResources().openRawResourceFd(R.raw.stop);
        
        startEngineId = soundPool.load(startEngine.getFileDescriptor(),startEngine.getStartOffset(),startEngine.getLength(),1);
        stopEngineId = soundPool.load(stopEngine.getFileDescriptor(),stopEngine.getStartOffset(),stopEngine.getLength(),1);
        runEngineId = soundPool.load(runEngine.getFileDescriptor(),runEngine.getStartOffset(),runEngine.getLength(),1); 
	}
	
	/**
	 * Start the sub moving
	 */
	void drive() {
		if (state != SubState.DRIVING) {
			//Play the engine start sound and follow it with the engine sustain sound loop
			state = SubState.DRIVING;
			startEngineStreamId = soundPool.play(startEngineId, .2f, .2f, 1, 0, 1.0f);
			new Handler().postDelayed(new Runnable() {
				public void run() {
					if (state == SubState.DRIVING) {
						soundPool.stop(startEngineStreamId);
						runEngineStreamId = soundPool.play(runEngineId, .2f, .2f, 1, -1, 1.0f);
					}
				}
			}, AudioUtil.getSoundDuration(context, R.raw.startup));
		}
	}
	
	/**
	 * Stop the sub
	 */
	void stop() {
		if (state != SubState.STOPPED) {
			//play the engine stop sound and stop the engine start sound
			state = SubState.STOPPED;
			soundPool.stop(startEngineStreamId);
			soundPool.stop(runEngineStreamId);
			stopEngineStreamId = soundPool.play(stopEngineId, .2f, .2f, 1, 0, 1.0f);
		}
	}
	
	void ping() {
		soundPool.play(pingId, .2f, .2f, 1, 0, 1.0f);
	}
	
}
