package com.kwhipke.blindsub;

import com.kwhipke.blindsub.physics.PhysicsEngine;
import com.kwhipke.blindsub.physics.SubmarineState;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.submarine.PlayerSubmarine;

/**
 * Encapsulates the running of a game (the subs competing in an arena, not the menu and other stuff). Handles
 * getting button presses from the Android device. The game does not start until start is called.
 * @author Kyle
 *
 */
public class Game {
	
	public Game() {
		
	}
	
	public void start() {
		//Create a physics engine to handle the physical interactions
		PhysicsEngine physEng = new PhysicsEngine();
		//Sound engine
		SoundEngine soundEng = new SoundEngine();
		
		//Create a player submarine and add it to the physics simulation
		PlayerSubmarine playerSub = new PlayerSubmarine(SubmarineState.ORIGIN,soundEng);
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
