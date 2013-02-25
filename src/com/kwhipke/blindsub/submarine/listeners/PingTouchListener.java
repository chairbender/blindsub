package com.kwhipke.blindsub.submarine.listeners;

import com.kwhipke.blindsub.Game;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Tells the game about pinging type events
 * @author Kyle
 *
 */
public class PingTouchListener extends SubmarineTouchListener {

	public PingTouchListener(Game currentGame) {
		super(currentGame);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			currentGame().subPing();
		}
		return false;
	}

}
