package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.physics.Heading;
import com.kwhipke.blindsub.units.Degrees;

/**
 * Encapsulates all that is needed to represent the Submarine's state
 * @author Kyle
 *
 */
public class SubmarineSpatialState {
	private Heading facing;
	
	public static final SubmarineSpatialState ORIGIN = new SubmarineSpatialState(new Heading(0));
	
	public SubmarineSpatialState(Heading initFacing) {
		this.facing = initFacing;
	}
	
	public Heading getHeading() {
		return facing;
	}

	/**
	 * Change the heading by the headingChange degrees (positive or negative). WRaps around if it goes below zero or above 360
	 * @param headingChange
	 */
	public void adjustHeading(Degrees headingChange) {
		facing.adjustBy(headingChange);
		
	}
	
}
