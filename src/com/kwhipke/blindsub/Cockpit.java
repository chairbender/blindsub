package com.kwhipke.blindsub;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ResourceBundle;

import com.kwhipke.blindsub.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Cockpit extends Activity {
	
	//Various buttons
	Button btnThrottle;
	Button btnPing;
	Button btnFire;
	
	Context context;
	
	//The player's submarine
    Submarine sub;
    
    //The tick rate of the physics engine (how frequently it updates)
    //it's the number of milliseconds between each update
    static final long UPDATE_INTERVAL_MILLISECONDS = 50;  
	
    //State
    boolean isPaused = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        
        setContentView(R.layout.activity_cockpit);
        
        //Initialize our sub
        sub = new Submarine(this,0,0,0);
        
        //Set the event handlers for all of the buttons.
        /*Throttle button*/
        btnThrottle = (Button)findViewById(R.id.btnThrottle);
        //When pressed, play the startup sound. When startup sound is done,
        //play the hold sound. Then when release, play the stop sound.
        btnThrottle.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motion) {
				if (motion.getAction() == MotionEvent.ACTION_DOWN) {
					sub.drive();
				} else if (motion.getAction() == MotionEvent.ACTION_UP) {
					sub.stop();
				}
				return false;
			}        	
        });
        
        btnPing = (Button)findViewById(R.id.btnPing);
        btnPing.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					sub.ping();
				}
				return false;
			}
        	
        });
        
        btnFire = (Button)findViewById(R.id.btnFire);
        btnFire.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					sub.fire();
				}
				return false;
			}
        });
        
        mainLoop();
    }
    
    //runs until paused
    private void mainLoop() {
        //Main loop
        new Thread(new Runnable() {
			@Override
			public void run() {
				while (!isPaused) {
					//Update the physics objects
					sub.tick(UPDATE_INTERVAL_MILLISECONDS);
					
					//Wait the update interval
					try {
						Thread.sleep(UPDATE_INTERVAL_MILLISECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
        }).start();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	if (!isPaused) {
	    	sub.onPause();
	    	isPaused = true;
    	}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if (isPaused) {
    		sub.onResume();
    		isPaused = false;
    		mainLoop();
    	}
    }

}
