package com.kwhipke.blindsub.submarine.control;

import com.kwhipke.blindsub.submarine.control.button.FireButtonOnTouchListener;
import com.kwhipke.blindsub.submarine.control.button.PingButtonOnTouchListener;
import com.kwhipke.blindsub.submarine.control.button.ThrottleOnTouchListener;
import com.kwhipke.blindsub.submarine.control.event.OnButtonChanged;
import com.kwhipke.blindsub.submarine.control.event.OnSteeringChanged;
import com.kwhipke.blindsub.submarine.control.event.OnThrottleChanged;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

/**
 * Controls the submarine using the android's tilt for steering, and android GUI buttons
 * for throttle full/off, ping, and firing a missile.
 * @author Kyle
 *
 */
public class AndroidController extends SubmarineController{
	
	/**
	 * 
	 * @param throttleButton android button that should be used for throttle on and off
	 * @param pingButton android button that should fire a ping
	 * @param fireButton android button that should fire a torpedo
	 * @param steeringChangedCallback what should happen when steering is changed
	 * @param throttleChangedCallback what should happen when the throttle is changed
	 * @param buttonChangedCallback what should happen when a button is pressed
	 */
	public AndroidController(SteeringSensorReader sensorReader, Button throttleButton, Button pingButton, Button fireButton, OnSteeringChanged steeringChangedCallback,
			final OnThrottleChanged throttleChangedCallback,
			OnButtonChanged buttonChangedCallback) {
		super(steeringChangedCallback, throttleChangedCallback, buttonChangedCallback);
		
		throttleButton.setOnTouchListener(new ThrottleOnTouchListener(throttleChangedCallback));
		
		pingButton.setOnTouchListener(new PingButtonOnTouchListener(buttonChangedCallback));
		
		fireButton.setOnTouchListener(new FireButtonOnTouchListener(buttonChangedCallback)); 
		
		sensorReader.setSteeringChangedCallback(steeringChangedCallback);
		
	}

}
