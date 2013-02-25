package com.kwhipke.blindsub.physics;

/**
 * Encapsulates a submarine's position
 * @author Kyle
 *
 */
public class SubmarinePosition {
	public float x,y;
	
	public static final SubmarinePosition ORIGIN = new SubmarinePosition(0,0); 
	public SubmarinePosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
