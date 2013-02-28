package com.kwhipke.blindsub.submarine.control.button;

import com.kwhipke.blindsub.submarine.control.SubmarineButton;
import com.kwhipke.blindsub.submarine.control.event.OnButtonChanged;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * ontouchlistener which simply invokes an onbuttonchanged callback (simulating a fire button press)
 * @author Kyle
 *
 */
public class FireButtonOnTouchListener implements OnTouchListener{
	private OnButtonChanged onButtonChanged;
	
	/**
	 * 
	 * @param onButtonChanged callback to invoke (indicating a fire button press) when the android button is touched
	 */
	public FireButtonOnTouchListener(OnButtonChanged onButtonChanged) {
		this.onButtonChanged = onButtonChanged;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			onButtonChanged.onButtonPressed(SubmarineButton.FIRE);
		} 
		return false;
	}

}
