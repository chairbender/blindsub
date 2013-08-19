package com.kwhipke.blindsub.submarine;

import com.kwhipke.blindsub.physics.PhysicsEngineController;
import com.kwhipke.blindsub.sound.SoundEngineController;
import com.kwhipke.blindsub.submarine.state.SubmarineState;
import com.kwhipke.blindsub.submarine.type.SubmarineType;

/**
 * Submarine that shoots missiles. Has a fire method.
 *
 */
public abstract class ShootingSubmarine extends Submarine {
    private boolean doShoot;
	
	public ShootingSubmarine(SubmarineState initialState, SubmarineType submarineType,SoundEngineController soundEngineController) {
		super(initialState,submarineType,soundEngineController);
	}
	
	/**
	 * Shoot the currently equipped missile in the direction this sub is facing.
     * @param physicsEngineController the controller to access the physics engine with
	 */
	protected void fire(PhysicsEngineController physicsEngineController) {
		Torpedo torpedo = new Torpedo(this.currentState.getCurrentHeading(),this);
		physicsEngineController.addObject(torpedo, physicsEngineController.getMyPosition());
	}

    protected void fire() {
        doShoot = true;
    }



    @Override
    public void tick(long elapsedMillis, PhysicsEngineController controller) {
        if (doShoot) {
            fire(controller);
            doShoot = false;
        }
    }
	
	
}
