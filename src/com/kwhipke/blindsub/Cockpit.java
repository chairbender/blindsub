package com.kwhipke.blindsub;

import android.content.Context;
import android.os.Bundle;
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
