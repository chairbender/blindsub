package com.kwhipke.blindsub.submarine.control.button;

import com.kwhipke.blindsub.submarine.control.SubmarineButton;
import com.kwhipke.blindsub.submarine.control.event.OnButtonChanged;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * ontouchlistener which simply invokes an onbuttonchanged callback (simulating a ping button press)
 * @author Kyle
 *
 */
public class PingButtonOnTouchListener implements OnTouchListener{
	private OnButtonChanged onButtonChanged;
	
	/**
	 * 
	 * @param onButtonChanged callback to invoke (indicating a ping button press) when the android button is touched
	 */
	public PingButtonOnTouchListener(OnButtonChanged onButtonChanged) {
		this.onButtonChanged = onButtonChanged;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			onButtonChanged.onButtonPressed(SubmarineButton.PING);
		} 
		return false;
	}

}
