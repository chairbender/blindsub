package com.kwhipke.blindsub;

//A physobj that just stays in place
public class StaticSub implements PhysObj{
	
	private double position[];
	
	public StaticSub(int x, int y) {
		position = new double[2];
		position[0] = x;
		position[1] = y;
	}
	
	@Override
	public void tick(long elapsedMillis) {
		
	}
	
	@Override
	public double[] getPosition() {
		return position;
	}

}
