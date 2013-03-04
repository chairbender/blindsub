package com.kwhipke.blindsub.submarine.state;

import com.kwhipke.blindsub.submarine.control.ThrottlePosition;


/**
 * Describes the status of a current submarine. Includes integrity and loadout and various other things.
 * @author Kyle
 *
 */
public class SubmarineStatus {
	private Integrity integrity;
	private ThrottlePosition throttle;
	
	public SubmarineStatus() {
		this.integrity = Integrity.MAXIMUM;
		this.throttle = new ThrottlePosition(0);
	}

	public ThrottlePosition getThrottle() {
		return throttle;
	}
}
