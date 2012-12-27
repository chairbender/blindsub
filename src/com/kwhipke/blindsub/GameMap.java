package com.kwhipke.blindsub;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pielot.openal.Buffer;
import org.pielot.openal.SoundEnv;
import org.pielot.openal.Source;

import com.kwhipke.blindsub.submarine.PlayerSubmarine;
import com.kwhipke.blindsub.submarine.StaticSub;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

/**
 * Represents the state of a game and the map every sub is playing in.
 * Handles updating the game state and interactions between game objects.
 * @author Kyle
 *
 */
public class GameMap implements Pausable {

	private PlayerSubmarine playerSub;
	
	//Stores a map between physics objects other than the player and their ping source
	private Map<PhysObj,Source> otherObjects;
	
	//stores all physics objects including the player sub
	private Set<PhysObj> physicalObjects;
	
	private SoundEnv env;
	
	private Activity parentActivity;
	
	private boolean isPaused;
	private boolean isLooping = false;
	
	//Speed that sound travels at in the water in this game
	private static final double SOUND_METERS_PER_SECOND = 30;
    //The tick rate of the physics engine (how frequently it updates)
    //it's the number of milliseconds between each update
    static final long UPDATE_INTERVAL_MILLISECONDS = 50;  
	
	
	/**
	 * 
	 * @param playerSub the player's submarine. must not be null
	 */
	public GameMap(Activity parentActivity, PlayerSubmarine playerSubmarine) {
		this.parentActivity = parentActivity;
		this.playerSub = playerSubmarine;
		this.otherObjects = new HashMap<PhysObj,Source>();
		this.physicalObjects = new HashSet<PhysObj>();
		
		//Prepare the ping sound that we will use for responding to
		//the player submarine's pings
		this.env = SoundEnv.getInstance(parentActivity);
		Buffer ping = null;
		try {
			ping = SoundManager.getSoundManager(parentActivity).getSound("pingresponse");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//Here's where we will create all our objects and their corresponding ping sources
		//For now have a dummy phys object
		StaticSub staticSub = new StaticSub(5,5);
		otherObjects.put(staticSub, env.addSource(ping));
		
		//Add event handler to play response pings when submarine 
		playerSub.setOnPingListener(new OnPingListener() {
			@Override
			public void onPing(double sourceX, double sourceY) {
				//for every physics object, figure out its orientation
				//from the player and play a ping based on that.
				
				double subPos[] = playerSub.getPosition();
				for (PhysObj physObj : otherObjects.keySet()) {
					//figure out the vector from the player to the other object
					double objPos[] = physObj.getPosition();
					double distance = Math.sqrt((objPos[0] - subPos[0])*(objPos[0]-subPos[0]) +
							(objPos[1] - subPos[1])*(objPos[1]-subPos[1]));
					
					//set the source up for the ping and play it after a time based on its distance
					//from the player
					final Source objSource = otherObjects.get(physObj);
					objSource.setPosition((float)(objPos[0] - subPos[0]), 
							(float)(objPos[1] - subPos[1]), 0f);
					//sound plays after it travels to the object and back
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Log.i("GameMap","playing response");
							objSource.play(false);
						}
					},  Math.round(((distance / SOUND_METERS_PER_SECOND) * 2)) * 1000);
					Log.i("GameMap", "time: " + (distance / (SOUND_METERS_PER_SECOND)*1000.0* 2));
					Log.i("GameMap", "distance: " + distance);
					Log.i("GameMap", "orientation: " + playerSub.getOrientation());
					Log.i("GameMap", "x, y, source pos: " + (objPos[0] - subPos[0]) + " " +
							(objPos[1] - subPos[1]));
				}
			}
		});
		
		this.physicalObjects.add(playerSubmarine);
		this.physicalObjects.add(staticSub);
	}
	
	/**
	 * Call this to start playing the map. Will pause and resume with onPause and onResume
	 * after it has been started
	 */
	public void startMainLoop() {
		isLooping = true;
		//Main loop
        new Thread(new Runnable() {
			@Override
			public void run() {
				while (!isPaused) {
					for (PhysObj obj : physicalObjects) {
						//Update the physics objects
						obj.tick(UPDATE_INTERVAL_MILLISECONDS);
						
						//If any collisions have happened, resolve them
						for (PhysObj otherObj : physicalObjects) {
							if (otherObj != obj) {
								obj.resolveCollision(otherObj);
							}
						}
					}
					
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
	public void onPause() {
		if (!isPaused)
			isPaused = true;
		
	}

	@Override
	public void onResume() {
		if (isPaused) {
			isPaused = false;
			if (isLooping) {
				startMainLoop();
			}
		}
	}

}