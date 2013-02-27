package com.kwhipke.blindsub.submarine.type;


/**
 * Describes the weapons and devices that a body is outfitted with. Each body has "slots"
 * where stuff can go.
 * @author Kyle
 *
 */
public class Loadout {
	private BodyType bodyType;
	
	public Loadout(BodyType bodyType) {
		this.bodyType = bodyType;
	}

}
