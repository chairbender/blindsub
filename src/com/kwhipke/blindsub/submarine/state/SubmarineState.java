package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.physics.Heading;
import com.kwhipke.blindsub.submarine.Torpedo;
import com.kwhipke.blindsub.submarine.control.Steering;
import com.kwhipke.blindsub.submarine.control.ThrottlePosition;
import com.kwhipke.blindsub.submarine.stats.Speed;
import com.kwhipke.blindsub.submarine.stats.TurningRadius;
import com.kwhipke.blindsub.submarine.type.SubmarineType;
import com.kwhipke.blindsub.units.Degrees;
import com.kwhipke.blindsub.units.Meters;

/**
 * Describes the position and status (like health and ammo) of the submarine, and the throttle and steering and stuff like that
 * @author Kyle
 *
 */
public class SubmarineState {

	private SubmarineSpatialState currentSpatialState;
	private SubmarineStatus currentStatus;
	
	public SubmarineState(SubmarineSpatialState initialSpatialState, SubmarineStatus initialStatus) {
		this.currentSpatialState = initialSpatialState;
		this.currentStatus = initialStatus;
	}
	
	
	/**
	 * Update the heading based on the current steering and speed. Use the subType to determine the stats to use to figure out
	 * how quickly it turns or moves.
	 * @param subType description of the submarine
	 * @param elapsedMilliseconds amount of time to hold that steering and simulate for
	 */
	public void tick(SubmarineType subType, long elapsedMilliseconds) {
		//Figure out how far the sub would travel. Then make it travel around the circle described by the turning radius.
		Speed currentSpeed = subType.getTopSpeed().throttledBy(currentStatus.getThrottle());
		TurningRadius turningRadius = subType.getTurningRadius();
		Meters displacement = new Meters(currentSpeed.displacement(elapsedMilliseconds));
		Degrees headingChange = turningRadius.getHeadingChange(currentStatus.currentSteering(),displacement);
		this.currentSpatialState.adjustHeading(headingChange);
	}

	/**
	 * Changes the position of the steering wheel, but does not change teh actual heading
	 * @param newSteering new steering to position the wheel at
	 */
	public void setSteering(Steering newSteering) {
		currentStatus.changeSteering(newSteering);
	}
	
	public Heading getCurrentHeading() {
		return currentSpatialState.getHeading();
	}
	
	public ThrottlePosition getCurrentThrottle() {
		return currentStatus.getThrottle();
	}


	/**
	 * 
	 * @param newThrottle new throttle to change to, instantly
	 */
	public void changeThrottle(ThrottlePosition newThrottle) {
		currentStatus.changeThrottle(newThrottle);
		
	}


	/**
	 * Reduces the hull integrity based on the submarineType and the torpedo
	 * @param submarineType type of the submarine damage is being done to
	 * @param other the missile that is hitting the submarine
	 */
	public void takeDamage(SubmarineType submarineType, Torpedo torpedo) {
		currentStatus.doDamage(submarineType.getDamageDone(torpedo));
		
	}


	/**
	 * 
	 * @return true if integrity reduced to zero or below
	 */
	public boolean isDestroyed() {
		return currentStatus.isDestroyed();
	}
}
