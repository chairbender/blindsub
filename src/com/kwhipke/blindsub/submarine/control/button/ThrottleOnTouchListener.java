package com.kwhipke.blindsub.submarine.control.button;

import com.kwhipke.blindsub.submarine.control.SubmarineButton;
import com.kwhipke.blindsub.submarine.control.ThrottlePosition;
import com.kwhipke.blindsub.submarine.control.event.OnButtonChanged;
import com.kwhipke.blindsub.submarine.control.event.OnThrottleChanged;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * ontouchlistener which simply invokes an onthrottlechanged callback (simulating a full throttle when button is pressed and stop when it is released)
 * @author Kyle
 *
 */
public class ThrottleOnTouchListener implements OnTouchListener{
	private OnThrottleChanged onThrottleChanged;
	
	/**
	 * 
	 * @param onButtonChanged callback to invoke (indicating a fire button press) when the android button is touched
	 */
	public ThrottleOnTouchListener(OnThrottleChanged onThrottleChanged) {
		this.onThrottleChanged = onThrottleChanged;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent motion) {
		if (motion.getAction() == MotionEvent.ACTION_DOWN) {
			onThrottleChanged.onThrottleChanged(new ThrottlePosition(1));
		} else if (motion.getAction() == MotionEvent.ACTION_UP) {
			onThrottleChanged.onThrottleChanged(new ThrottlePosition(0));
		}
		return false;
	}

}
