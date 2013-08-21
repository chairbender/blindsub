package com.kwhipke.blindsub.physics;

import android.util.Log;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.units.Degrees;
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
        return heading.getXComponent(speed);

	}
	
	private Speed getYComponent() {
        return heading.getYComponent(speed);
	}

    /**
     *
     * @return the heading of the velocity vector (i.e. which way it points
     */
    public Degrees heading() {
        return heading.getDegrees();
    }
}
