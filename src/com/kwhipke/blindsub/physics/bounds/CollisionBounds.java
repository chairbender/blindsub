package com.kwhipke.blindsub.physics.bounds;

import com.kwhipke.blindsub.physics.Position;
import com.kwhipke.blindsub.units.Meters;

/**
 * Describes the collision model of an object. For now, it's all just circles
 * @author Kyle
 *
 */
public interface CollisionBounds {

	/**
	 * Checks for a collision between two bounds if the origins of the bounds are at origin and otherOrigin
	 * @param origin x and y of the origin of this collision bounds
	 * @param otherOrigin x and y of the origin of the collision bounds of the other 
	 * @param otherBounds collision bounds to check against
	 * @return true if collision
	 */
	public boolean checkCollision(Position origin, Position otherOrigin, CollisionBounds otherBounds);
}
