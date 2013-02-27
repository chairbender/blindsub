package com.kwhipke.blindsub.submarine.state;


/**
 * Describes the status of a current submarine. Includes integrity and loadout and various other things.
 * @author Kyle
 *
 */
public class SubmarineStatus {
	private Integrity integrity;
	
	public SubmarineStatus() {
		this.integrity = Integrity.MAXIMUM;
	}
}
