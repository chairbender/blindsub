package com.kwhipke.blindsub.submarine.type;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a description of a list of "equipment slots" where weapons and munitions and stuff can be equipped.
 * @author Kyle
 *
 */
public class EquipSlotsDescription {
	private List<EquipSlot> slots;
	
	//description with just 2 slots - ping and torpedo slots (actually any slots but we don't have type restrictions right now)
	private static final List<EquipSlot> TORPEDO_PING_LIST = new ArrayList<EquipSlot>();
	public static final EquipSlotsDescription TORPEDO_PING = new EquipSlotsDescription(TORPEDO_PING_LIST);
	
	
	public EquipSlotsDescription(List<EquipSlot> slots) {
		if (TORPEDO_PING_LIST.size() == 0) {
			 TORPEDO_PING_LIST.add(new EquipSlot());
			 TORPEDO_PING_LIST.add(new EquipSlot());
		}
		this.slots = slots;
	}
}
