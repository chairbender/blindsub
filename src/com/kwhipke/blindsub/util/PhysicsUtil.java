package com.kwhipke.blindsub.util;

import com.kwhipke.blindsub.physics.PhysObj;

public class PhysicsUtil {
	
	/**
	 * Gets the amount in meters that the two collision circles for these objects overlap.
	 * Negative or zero if no overlap.
	 * @param source a physical object
	 * @param other the object to check for overlap with
	 * @return
	 */
	public static double getOverlap(PhysObj source, PhysObj other) {
		double otherX = other.getPosition()[0] - source.getPosition()[0];
		double otherY = other.getPosition()[1] - source.getPosition()[1];
		
		double totalRadii = source.getRadius() + other.getRadius();
		double distance = Math.sqrt(otherX * otherX + otherY * otherY);
		return totalRadii - distance;
	}

	public static float radiansToDegrees(float f) {
		return f * 57.2957795131f;
	}
}
