package com.kwhipke.blindsub;

import android.widget.Button;

import com.kwhipke.blindsub.physics.PhysicsEngine;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.submarine.ControlledSubmarine;
import com.kwhipke.blindsub.submarine.control.AndroidController;
import com.kwhipke.blindsub.submarine.control.SteeringSensorReader;
import com.kwhipke.blindsub.submarine.state.SubmarineSpatialState;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.state.SubmarineStatus;
import com.kwhipke.blindsub.submarine.type.SubmarineTypes;

//TODO: CUrrent goal, get it to a state where it compiles, you can drive the player sub around, and you can ping the other sub

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
		SoundEngine soundEng = new SoundEngine(physEng,playerSub);
		
		//Create a player-controlled submarine and add it to the game's physics simulation
		 playerSub = new ControlledSubmarine(new SubmarineState(SubmarineSpatialState.ORIGIN,new SubmarineStatus()),SubmarineTypes.BASIC);
		//TODO: Hook up the buttons. Implement the physics and figuring out the submarine's stats based on the body and engine.
		AndroidController androidController = new AndroidController(new SteeringSensorReader(),btnThrottle,btnPing,btnFire,playerSub,playerSub,playerSub);
		physEng.addObject(playerSub);
	}

	/**
	 * Tell the game that the sub should drive
	 */
	public void subDrive() {

		
	}

	/**
	 * Tell the game that the sub should stop
	 */
	public void subStop() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Tell the game that the sub should ping
	 */
	public void subPing() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Tell the game that the sub should fire
	 */
	public void subFire() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Tell the game to pause (if not paused)
	 */
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Tell the game to resume (if paused)
	 */
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
}
