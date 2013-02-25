package com.kwhipke.blindsub.submarine.listeners;

import android.view.View.OnTouchListener;

import com.kwhipke.blindsub.Game;

/**
 * An ontouchlistener that is aware of the currently running game.
 * @author Kyle
 *
 */
public abstract class SubmarineTouchListener implements OnTouchListener {
	private Game game;

	public SubmarineTouchListener(Game currentGame) {
		this.game = game;
	}
	
	protected Game currentGame() {
		return game;
	}
}
