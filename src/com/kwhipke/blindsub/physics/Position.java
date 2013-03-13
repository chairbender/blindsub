package com.kwhipke.blindsub.physics;

/**
 * Encapsulates a position in 2d space
 * @author Kyle
 *
 */
public class Position {
	public float x,y;
	
	public static final Position ORIGIN = new Position(0,0); 
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * 
	 * @param newPosition position to add
	 * @return a new position obtained by adding the x and y coords of the positions
	 */
	public Position add(Position newPosition) {
		return new Position(this.x + newPosition.x,this.y + newPosition.y);
	}
}
