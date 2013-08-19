package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.submarine.Damage;
import com.kwhipke.blindsub.submarine.control.Steering;
import com.kwhipke.blindsub.submarine.control.ThrottlePosition;


/**
 * Describes the status of a current submarine. Includes integrity and loadout and various other things.
 * @author Kyle
 *
 */
public class SubmarineStatus {
	private Integrity integrity;
	private ThrottlePosition throttle;
	private Steering steering;
	
	public SubmarineStatus() {
		this.integrity = Integrity.MAXIMUM;
		this.throttle = new ThrottlePosition(0);
		this.steering = new Steering(0);
	}

	public ThrottlePosition getThrottle() {
		return throttle;
	}

	public void changeThrottle(ThrottlePosition newThrottle) {
		throttle = newThrottle;
	}
	
	public void changeSteering(Steering newSteering) {
		this.steering = newSteering;
	}

	public Steering currentSteering() {
		return steering;
	}
	
	public void doDamage(Damage toDo) {
		integrity.takeDamage(toDo);
	}

	public boolean isDestroyed() {
		return integrity.isZero();
	}
}
