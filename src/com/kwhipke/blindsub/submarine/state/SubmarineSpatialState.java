package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.physics.Heading;
import com.kwhipke.blindsub.physics.Position;
import com.kwhipke.blindsub.submarine.control.Steering;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.submarine.stats.TurningRadius;
import com.kwhipke.blindsub.units.Degrees;
import com.kwhipke.blindsub.units.Meters;

/**
 * Encapsulates all that is needed to represent the Submarine's state
 * @author Kyle
 *
 */
public class SubmarineSpatialState {
	private Heading facing;
	private Position position;
	
	public static final SubmarineSpatialState ORIGIN = new SubmarineSpatialState(Position.ORIGIN,new Heading(0));
	
	public SubmarineSpatialState(Position initPos, Heading initFacing) {
		this.position = initPos;
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
