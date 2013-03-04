package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.physics.Heading;
import com.kwhipke.blindsub.physics.Position;

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
	
}
