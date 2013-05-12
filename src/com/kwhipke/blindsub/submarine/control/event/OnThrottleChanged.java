package com.kwhipke.blindsub.submarine.control.event;

import com.kwhipke.blindsub.submarine.control.ThrottlePosition;

import java.io.IOException;


/**
 * Callback to be invoked when the position of the throttle is changed.
 * @author Kyle
 *
 */
public interface OnThrottleChanged {

	public void onThrottleChanged(ThrottlePosition newThrottle) throws IOException;
}
