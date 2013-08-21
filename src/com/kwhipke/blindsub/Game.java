package com.kwhipke.blindsub;

import android.widget.Button;

import com.kwhipke.blindsub.physics.Heading;
import com.kwhipke.blindsub.physics.PhysicsEngine;
import com.kwhipke.blindsub.physics.Position;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.submarine.ControlledSubmarine;
import com.kwhipke.blindsub.submarine.Rock;
import com.kwhipke.blindsub.submarine.control.AndroidController;
import com.kwhipke.blindsub.submarine.control.SteeringSensorReader;
import com.kwhipke.blindsub.submarine.state.SubmarineSpatialState;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.state.SubmarineStatus;
import com.kwhipke.blindsub.submarine.type.SubmarineTypes;

//TODO: Fix the sub steering and movement glitches. Come up with a way to figure out position easily for debugging.

/**
 * Encapsulates the running of a game (the subs competing in an arena, not the menu and other stuff). Handles
 * getting button presses from the Android device. The game does not start until start is called.
 * @author Kyle
 *
 */
public class Game {
	
	public Game() {
		
	}
	
	/**
	 * Buttons to use for the player submarine's controls
	 * @param btnPing
	 * @param btnFire
	 * @param btnThrottle
	 */
	public void start(Button btnPing, Button btnFire, Button btnThrottle) {
		//Create a physics engine to handle the physical interactions
		PhysicsEngine physEng = new PhysicsEngine(10);
        ControlledSubmarine playerSub;
		//Sound engine to handle playing of audio events and interacting with the physics engine to handle the movement of sound
		SoundEngine soundEng = new SoundEngine(physEng);
		
		//Create a player-controlled submarine and add it to the game's physics simulation
		 playerSub = new ControlledSubmarine(new SubmarineState(SubmarineSpatialState.ORIGIN,new SubmarineStatus()),SubmarineTypes.BASIC,soundEng);

        soundEng.setListener(playerSub);
		AndroidController androidController = new AndroidController(new SteeringSensorReader(),btnThrottle,btnPing,btnFire,playerSub,playerSub,playerSub);
		physEng.addObject(playerSub, Position.ORIGIN);

        Rock rock = new Rock(new SubmarineState(new SubmarineSpatialState(new Heading(0)),new SubmarineStatus()),SubmarineTypes.BASIC,soundEng);
        physEng.addObject(rock,new Position(50,50));
        physEng.start();
	}

    public void pause() {
        //TODO: implement
    }

    public void resume() {
        //TODO: implement
    }
}
