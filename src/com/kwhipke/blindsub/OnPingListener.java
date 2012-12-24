package com.kwhipke.blindsub;

public interface OnPingListener {
	
	/**
	 * Do something when a ping occurs at the specified x and y coordinates
	 * @param sourceX
	 * @param sourceY
	 */
	public void onPing(double sourceX, double sourceY);
}
