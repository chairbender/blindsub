package com.kwhipke.blindsub.physics;

import android.util.Log;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.units.Degrees;
import com.kwhipke.blindsub.units.Meters;

/**
 * Encapsulates a heading. 0 is east 90 is north
 * @author Kyle
 *
 */
public class Heading {
	private Degrees heading;
	
	public Heading(float heading) {
		this.heading = new Degrees(heading);
	}
	
	public Heading(Degrees heading) {
		this.heading = heading;
	}
	
	public Degrees getDegrees() {
		return heading;
	}
	
	public float getRadians() {
		return (float) (heading.getDegrees() * Math.PI / 180.0);
	}

	/**
	 * 
	 * @param headingChange add headingCHange to the facing (even if it is negative).
	 * wraps around if less than zero or greater than 360. So it behaves as you would expect for a heading
	 */
	public void adjustBy(Degrees headingChange) {
		heading.add(headingChange);
	}

    /**
     *
     * @param currentSpeedInThisHeading the velocity to get the x component of, assuming velocity is in this heading's direction
     * @return the x component of the velocity in this direction
     */
    public Speed getYComponent(Speed currentSpeedInThisHeading) {
        /*if (heading.getDegrees() >= 0 && heading.getDegrees() < 90) {
            return new Speed(new Meters(Math.sin(getRadians()) * currentSpeedInThisHeading.getVelocity()));
        } else if (heading.getDegrees() >= 90 && heading.getDegrees() < 180) {
            return new Speed(new Meters(-1 * Math.sin(getRadians()) * currentSpeedInThisHeading.getVelocity()));
        } else if (heading.getDegrees() >= 180 && heading.getDegrees() < 270) {
            return new Speed(new Meters(Math.sin(getRadians()) * currentSpeedInThisHeading.getVelocity()));
        } else {*/
        return new Speed(new Meters(Math.sin(getRadians()) * currentSpeedInThisHeading.getVelocity()));
        /*}*/

    }

    public Speed getXComponent(Speed currentSpeedInThisHeading) {
        /*if (heading.getDegrees() >= 0 && heading.getDegrees() < 90) {
            return new Speed(new Meters(Math.cos(getRadians()) * currentSpeedInThisHeading.getVelocity()));
        } else if (heading.getDegrees() >= 90 && heading.getDegrees() < 180) {
            return new Speed(new Meters(-1 * Math.cos(getRadians()) * currentSpeedInThisHeading.getVelocity()));
        } else if (heading.getDegrees() >= 180 && heading.getDegrees() < 270) {
            return new Speed(new Meters(Math.cos(getRadians()) * currentSpeedInThisHeading.getVelocity()));
        } else {*/
        return new Speed(new Meters(Math.cos(getRadians()) * currentSpeedInThisHeading.getVelocity()));
        /*}*/
    }
}
