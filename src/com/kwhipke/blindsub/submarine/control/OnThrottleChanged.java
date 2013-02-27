package com.kwhipke.blindsub.submarine.control;


/**
 * Callback to be invoked when the position of the throttle is changed.
 * @author Kyle
 *
 */
public interface OnThrottleChanged {

	public void onThrottleChanged(ThrottlePosition newThrottle);
}
