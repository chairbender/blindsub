package com.kwhipke.blindsub.submarine.control;

/**
 * Event to fire when a buttom on the submarine's control surface is pressed.
 * Fires when initially pressed down, held, and released.
 * @author Kyle
 *
 */
public interface OnButtonChanged {
	public void onButtonPressed(SubmarineButton whichButton);
	public void onButtonHeld(SubmarineButton whichButton);
	public void onButtonReleased(SubmarineButton whichButton);
}
