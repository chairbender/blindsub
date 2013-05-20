package com.kwhipke.blindsub.engine;

import java.io.IOException;

/**
 * represents a class that does stuff every tick of the game simulation
 * @author Kyle
 */
public interface Ticker {

	public void tick() throws IOException;
}
