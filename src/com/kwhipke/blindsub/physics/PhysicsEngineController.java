package com.kwhipke.blindsub.physics;

/**
 * Provides the ability for a physobj to interact with the physics engine.
 * User: Kyle
 * Date: 8/16/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhysicsEngineController {
    private PhysicsEngine engine;
    private PhysObj thisObject;

    /**
     *
     * @param engine the physics engine that the controller provides access to
     * @param thisObject the object that is provided access to the physics engine.
     */
    public PhysicsEngineController(PhysicsEngine engine, PhysObj thisObject) {
        this.engine = engine;
        this.thisObject = thisObject;
    }

    /**
     * Gets the position of the object this controller is for.
     */
    public Position getMyPosition() {
        return engine.getPositionOfPhysObj(thisObject);
    }

    /**
     * adds a physical object to the physics engine at a particular location.
     * @param toAdd object to add
     * @param initialPosition position to add the object
     */
    public void addObject(PhysObj toAdd, Position initialPosition) {
        engine.addObject(toAdd,initialPosition);
    }
}
