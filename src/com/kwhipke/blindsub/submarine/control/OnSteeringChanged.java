package com.kwhipke.blindsub.submarine.control;

/**
 * Callback that is invoked when the wheel of the submarine is moved to a new position
 * @author Kyle
 *
 */
public interface OnSteeringChanged {
	public void onSteeringChanged(Steering newSteering);
}
