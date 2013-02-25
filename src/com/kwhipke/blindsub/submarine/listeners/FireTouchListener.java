package com.kwhipke.blindsub.submarine.listeners;

import com.kwhipke.blindsub.Game;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Tells the game about weapon firing type events
 * @author Kyle
 *
 */
public class FireTouchListener extends SubmarineTouchListener {

	public FireTouchListener(Game currentGame) {
		super(currentGame);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			currentGame().subFire();
		}
		return false;
	}

}
