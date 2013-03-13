package com.kwhipke.blindsub.physics;

import com.kwhipke.blindsub.units.Meters;

/**
 * Describes the collision model of an object. For now, it's all just circles
 * @author Kyle
 *
 */
public class CollisionBounds {
	private Meters collisionRadius;
	
	public CollisionBounds(Meters collisionRadius) {
		this.collisionRadius = collisionRadius;
	}
	
	/**
	 * Checks for a collision between two bounds if the origins of the bounds are at origin and otherOrigin
	 * @param origin x and y of the origin of this collision bounds
	 * @param otherOrigin x and y of the origin of the collision bounds of the other 
	 * @param otherBounds collision bounds to check against
	 * @return true if collision
	 */
	public boolean checkCollision(Position origin, Position otherOrigin, CollisionBounds otherBounds) {
		//Get the distance between the origins and check if that is less than the sum of their radii
		double distance = Math.sqrt(origin.x * otherOrigin.x + origin.y * otherOrigin.y);
		double totalRadii = this.collisionRadius.getMeters() + otherBounds.collisionRadius.getMeters();
		return distance < totalRadii;
	}
}
