package com.kwhipke.blindsub.submarine.type;


/**
 * Describes a type of submarine (like, a model of submarine).
 * Includes tje submarine's body (what it's made of and what it can hold) and 
 * loadout (how the body is outfitted with weapons and devices)
 * @author Kyle
 *
 */
public class SubmarineType {
	private BodyType bodyType;
	private Loadout loadout;
	
	public SubmarineType(BodyType bodyType, Loadout loadout) {
		this.bodyType = bodyType;
		this.loadout = loadout;
	}
}
