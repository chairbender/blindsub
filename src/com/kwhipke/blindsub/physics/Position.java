package com.kwhipke.blindsub.physics;

import com.kwhipke.blindsub.units.Meters;

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

    /**
     *
     * @param otherPosition the position to get the distance to
     * @return the distance between the two positions in meters
     */
    public Meters distanceTo(Position otherPosition) {
        return new Meters(Math.sqrt( Math.abs(Math.pow(x - otherPosition.x,2)) + Math.abs(Math.pow(y - otherPosition.y,2))));
    }
}
