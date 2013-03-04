package com.kwhipke.blindsub;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ResourceBundle;

import com.kwhipke.blindsub.R;
import com.kwhipke.blindsub.submarine.ControlledSubmarine;
import com.kwhipke.blindsub.submarine.listeners.FireTouchListener;
import com.kwhipke.blindsub.submarine.listeners.PingTouchListener;
import com.kwhipke.blindsub.submarine.listeners.ThrottleTouchListener;

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
public class Cockpit extends BlindSubActivity {
	Context context;
	Game game;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        
        setContentView(R.layout.activity_cockpit);
        
        //Initialize a game
        game = new Game();
        
        //Set the event handlers for all of the buttons.
        Button btnThrottle = (Button)findViewById(R.id.btnThrottle);
        Button btnPing = (Button)findViewById(R.id.btnPing);
        Button btnFire = (Button)findViewById(R.id.btnFire);
        
        game.start(btnPing,btnFire,btnThrottle);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	game.pause();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	game.resume();
    }

}
