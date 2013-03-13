package com.kwhipke.blindsub.physics;

import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.units.Meters;

/**
 * A speed and direction
 * @author Kyle
 *
 */
public class VelocityVector {
	private Speed speed;
	private Heading heading;
	
	public VelocityVector(Speed speed, Heading heading) {
		this.speed = speed;
		this.heading = heading;
	}

	/**
	 * Calculate displacement from origin if point traveled on this vector for the given number of milliseconds
	 * @param milliseconds how long to travel in this velocityvector
	 * @return the new position as displacement from the origin
	 */
	public Position displacement(long milliseconds) {
		// Get the components
		Speed xVelocity = this.getXComponent();
		Speed yVelocity = this.getYComponent();
		return new Position(xVelocity.displacement(milliseconds),yVelocity.displacement(milliseconds));
	}

	private Speed getXComponent() {
		if (heading.getDegrees() >= 0 && heading.getDegrees() < 90) {
			return new Speed(new Meters(Math.sin(heading.getRadians()) * speed.getVelocity()));
		} else if (heading.getDegrees() >= 90 && heading.getDegrees() < 180) {
			return new Speed(new Meters(-1 * Math.sin(heading.getRadians()) * speed.getVelocity()));
		} else if (heading.getDegrees() >= 180 && heading.getDegrees() < 270) {
			return new Speed(new Meters(Math.sin(heading.getRadians()) * speed.getVelocity()));
		} else {
			return new Speed(new Meters(-1 * Math.sin(heading.getRadians()) * speed.getVelocity()));
		}
	}
	
	private Speed getYComponent() {
		if (heading.getDegrees() >= 0 && heading.getDegrees() < 90) {
			return new Speed(new Meters(Math.cos(heading.getRadians()) * speed.getVelocity()));
		} else if (heading.getDegrees() >= 90 && heading.getDegrees() < 180) {
			return new Speed(new Meters(-1 * Math.cos(heading.getRadians()) * speed.getVelocity()));
		} else if (heading.getDegrees() >= 180 && heading.getDegrees() < 270) {
			return new Speed(new Meters(Math.cos(heading.getRadians()) * speed.getVelocity()));
		} else {
			return new Speed(new Meters(-1 * Math.cos(heading.getRadians()) * speed.getVelocity()));
		}
	}
}
