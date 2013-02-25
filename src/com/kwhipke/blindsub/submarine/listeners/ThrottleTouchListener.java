package com.kwhipke.blindsub.submarine.listeners;

import com.kwhipke.blindsub.Game;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Tells the game about throttle type events
 * @author Kyle
 *
 */
public class ThrottleTouchListener extends SubmarineTouchListener {

	public ThrottleTouchListener(Game currentGame) {
		super(currentGame);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent motion) {
		if (motion.getAction() == MotionEvent.ACTION_DOWN) {
			currentGame().subDrive();
		} else if (motion.getAction() == MotionEvent.ACTION_UP) {
			currentGame().subStop();
		}
		return false;
	}

}
