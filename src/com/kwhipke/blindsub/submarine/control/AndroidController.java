package com.kwhipke.blindsub.submarine.control;

/**
 * Controls the submarine using the android's tilt for steering, and android GUI buttons
 * for throttle full/off, ping, and firing a missile.
 * @author Kyle
 *
 */
public class AndroidController extends SubmarineController{
	
	//TODO: Finish
	/**
	 * 
	 * @param throttleButton
	 * @param pingButton
	 * @param fireButton
	 * @param steeringChangedCallback
	 * @param throttleChangedCallback
	 * @param buttonChangedCallback
	 */
	public AndroidController(Button throttleButton, Button pingButton, Button fireButton, OnSteeringChanged steeringChangedCallback,
			OnThrottleChanged throttleChangedCallback,
			OnButtonChanged buttonChangedCallback) {
		super(steeringChangedCallback, throttleChangedCallback, buttonChangedCallback);
	}

}
