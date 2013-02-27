package com.kwhipke.blindsub.submarine.state;

/**
 * Describes the position and status (like health and ammo) of the submarine
 * @author Kyle
 *
 */
public class SubmarineState {

	private SubmarineSpatialState currentSpatialState;
	private SubmarineStatus currentStatus;
	
	public SubmarineState(SubmarineSpatialState initialSpatialState, SubmarineStatus initialStatus) {
		this.currentSpatialState = initialSpatialState;
		this.currentStatus = initialStatus;
	}
}
