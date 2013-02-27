package com.kwhipke.blindsub.submarine.type;

/**
 * Defines some types of submarines
 * @author Kyle
 *
 */
public abstract class SubmarineTypes {

	public static final SubmarineType BASIC = 
		new SubmarineType(
				BodyTypes.BASIC, 
				new Loadout(BodyTypes.BASIC)
	);
}
