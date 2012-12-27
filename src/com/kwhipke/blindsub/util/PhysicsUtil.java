package com.kwhipke.blindsub.util;

import com.kwhipke.blindsub.PhysObj;

public class PhysicsUtil {
	//Returns the x and y components of the velocity vector basd on the
	//current heading and the velocity (x is the first and y is the second
	public static double[] getVelocityComponents(double heading,double velocity) {
		double result[] = new double[2];
		double headingRadians = heading * Math.PI / 180;
		
		if (headingRadians >= 0 && headingRadians < 90) {
			result[0] = Math.sin(headingRadians) * velocity;
			result[1] = Math.cos(headingRadians) * velocity;
		} else if (headingRadians >= 90 && headingRadians < 180) {
			result[0] = -1 * Math.sin(headingRadians) * velocity;
			result[1] = -1 * Math.cos(headingRadians) * velocity;
		} else if (headingRadians >= 180 && headingRadians < 270) {
			result[0] = Math.sin(headingRadians) * velocity;
			result[1] = Math.cos(headingRadians) * velocity;
		} else {
			result[0] = -1 * Math.sin(headingRadians) * velocity;
			result[1] = -1 * Math.cos(headingRadians) * velocity;
		}
		
		return result;
	}
	
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
}
