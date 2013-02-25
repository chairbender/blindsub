package com.kwhipke.blindsub.physics;

/**
 * Encapsulates all that is needed to represent the Submarine's state
 * @author Kyle
 *
 */
public class SubmarineState {
	private SubmarineFacing facing;
	private SubmarinePosition position;
	
	public static final SubmarineState ORIGIN = new SubmarineState(SubmarinePosition.ORIGIN,new SubmarineFacing(0));
	
	public SubmarineState(SubmarinePosition initPos, SubmarineFacing initFacing) {
		this.position = initPos;
		this.facing = initFacing;
	}
	
}
