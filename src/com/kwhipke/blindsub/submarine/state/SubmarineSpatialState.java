package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.physics.Heading;
import com.kwhipke.blindsub.physics.Position;
import com.kwhipke.blindsub.submarine.control.Steering;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.submarine.stats.TurningRadius;
import com.kwhipke.blindsub.units.Degrees;
import com.kwhipke.blindsub.units.Meters;

/**
 * Encapsulates all that is needed to represent the Submarine's state
 * @author Kyle
 *
 */
public class SubmarineSpatialState {
	private Heading facing;
	private Position position;
	
	public static final SubmarineSpatialState ORIGIN = new SubmarineSpatialState(Position.ORIGIN,new Heading(0));
	
	public SubmarineSpatialState(Position initPos, Heading initFacing) {
		this.position = initPos;
		this.facing = initFacing;
	}
	
	public Heading getHeading() {
		return facing;
	}
	
	/**
	 * Update the heading based on the position of the wheel, the minimum turning radius of the sub, the speed of the sub, and the elapsed time to hold that steering for
	 * @param currentSteering position of the wheel
	 * @param turningRadius minimum turning radius of the sub
	 * @param elapsedMilliseconds amount of time to hold that steering and simulate for
	 */
	public void steer(Steering currentSteering, TurningRadius turningRadius,Speed speed,long elapsedMilliseconds) {
		//Figure out how far the sub would travel. Then make it travel around the circle described by the turning radius.
		Meters displacement = new Meters(speed.displacement(elapsedMilliseconds));
		Degrees headingChance = turningRadius.getHeadingChange(currentSteering,displacement);
	}
	
}
