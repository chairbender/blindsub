package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.*;
import com.kwhipke.blindsub.physics.PhysObj;
/**
 * A physical object that follows a basically straight line through the water
 * at a constant speed and is destroyed when it hits something. If it hits a sub it probably does damage
 * @author Kyle
 *
 */
public abstract class Bullet implements PhysObj{
	private PhysObj creator;
	
	public Bullet(PhysObj creator) {
		this.creator = creator;
	}
	
	public abstract double getDamage();
	
	/**
	 * Get the object that shot this bullet
	 * @return
	 */
	public PhysObj getCreator() {
		return creator;
	}
}
