package com.kwhipke.blindsub.submarine.stats;

import com.kwhipke.blindsub.submarine.control.Steering;
import com.kwhipke.blindsub.units.Degrees;
import com.kwhipke.blindsub.units.Meters;

/**
 * The turning radius in meters.
 * @author Kyle
 *
 */
public class TurningRadius {
	private Meters radius;
	
	public TurningRadius(Meters radius) {
		this.radius = radius;
	}

	/**
	 * Given that an object's steering wheel is at currentSteering, and the object travels
	 * displacement meters, figure out how the object's heading would change, with clockwise being negative degrees.
	 */
	public Degrees getHeadingChange(Steering currentSteering,
			Meters displacement) {
		//Get circumference of turning circle.
		double circumference = (2 * Math.PI * radius.getMeters());
		//Figure out change in angle if traveling along turning circle.
		//Traveling the full circumference would be 180 degrees
		return new Degrees(-1 * (((displacement.getMeters() / circumference) * 180) * currentSteering.getSteeringScalar()));
	}

}
