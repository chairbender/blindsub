package com.kwhipke.blindsub.submarine.control;

/**
 * A button on the submarine's dashboard/control surface. Right now, is just a specific type, and it's assumed
 * all dashboards have the same possible buttons. So there's 1 ping, one fire, etc...
 *
 * @author Kyle
 *
 */
public class SubmarineButton {
	private SubmarineButtonType type;

	public static final SubmarineButton PING = new SubmarineButton(SubmarineButtonType.PING);

	public static final SubmarineButton FIRE = new SubmarineButton(SubmarineButtonType.FIRE);
	
	public SubmarineButton(SubmarineButtonType type) {
		this.type = type;
	}
	
	public SubmarineButtonType getButtonType() {
		return type;
	}


}
