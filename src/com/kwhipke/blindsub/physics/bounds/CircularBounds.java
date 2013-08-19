package com.kwhipke.blindsub.physics.bounds;

import com.kwhipke.blindsub.physics.Position;
import com.kwhipke.blindsub.units.Meters;

/**
 * Created with IntelliJ IDEA.
 * User: Kyle
 * Date: 8/18/13
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CircularBounds implements CollisionBounds {
    private Meters collisionRadius;

    public CircularBounds(Meters collisionRadius) {
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
        if (otherBounds instanceof CircularBounds) {
            CircularBounds otherCircularBounds = (CircularBounds) otherBounds;
            //Get the distance between the origins and check if that is less than the sum of their radii
            double distance = Math.sqrt(origin.x * otherOrigin.x + origin.y * otherOrigin.y);
            double totalRadii = this.collisionRadius.getMeters() + otherCircularBounds.collisionRadius.getMeters();
            return distance < totalRadii;
        } else {
            //TODO: If other collision types, handle them here. The collision code should def go in a helper class though
            return false;
        }
    }
}
