package com.kwhipke.blindsub.submarine.control;


/**
 * Provides a way to control the actions of a submarine.
 * Turning, using the engine, and firing weapons/using devices are all things a controller can do.
 * A submarinecontroller is just a class that invokes callbacks when the controller does stuff.
 * 
 * All classes that extend this will invoked the callbacks when they want the controls of the imaginary submarine
 * to be changed in any way.
 * @author Kyle
 *
 */
public abstract class SubmarineController {
	
	protected final OnSteeringChanged steeringCallback;
	protected final OnThrottleChanged throttleCallback;
	protected final OnButtonChanged buttonCallback;
	/**
	 * 
	 * @param steeringChangedCallback callback that will be invoked when controller changes target steering
	 * @param throttleChangedCallback callback invoked when controller changes target throttle
	 * @param buttonPressedCallback callback invoked when controller presses, holds, or releases a button (i.e. 
	 * indicates that a button on the imaginary submarine's control surface has been interacted with)
	 */
	public SubmarineController(
			OnSteeringChanged steeringChangedCallback,
			OnThrottleChanged throttleChangedCallback,
			OnButtonChanged buttonChangedCallback) {
		this.steeringCallback = steeringChangedCallback;
		this.throttleCallback = throttleChangedCallback;
		this.buttonCallback = buttonChangedCallback;
	}
	
}
