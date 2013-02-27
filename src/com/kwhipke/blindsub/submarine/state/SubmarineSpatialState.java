package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.physics.SubmarineFacing;
import com.kwhipke.blindsub.physics.SubmarinePosition;

/**
 * Encapsulates all that is needed to represent the Submarine's state
 * @author Kyle
 *
 */
public class SubmarineSpatialState {
	private SubmarineFacing facing;
	private SubmarinePosition position;
	
	public static final SubmarineSpatialState ORIGIN = new SubmarineSpatialState(SubmarinePosition.ORIGIN,new SubmarineFacing(0));
	
	public SubmarineSpatialState(SubmarinePosition initPos, SubmarineFacing initFacing) {
		this.position = initPos;
		this.facing = initFacing;
	}
	
}
