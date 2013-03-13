package com.kwhipke.blindsub.submarine.type;

/**
 * Describes a equip slot - which is just the type of equipment that can be equipped in the slot. For now, any equpment can go in any slot.
 * @author Kyle
 *
 */
public class EquipSlot {
	private EquipType type;
	private enum EquipType {
		ANY
	}
	
	public EquipSlot() {
		this.type = EquipType.ANY;
	}
}
