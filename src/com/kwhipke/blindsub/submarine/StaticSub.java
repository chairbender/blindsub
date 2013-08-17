/*
package com.kwhipke.blindsub.submarine;

import java.io.IOException;

import org.pielot.openal.SoundEnv;
import org.pielot.openal.Source;

import android.app.Activity;
import android.util.Log;

import com.kwhipke.blindsub.GameEngine;
import com.kwhipke.blindsub.sound.SoundEngine;
import com.kwhipke.blindsub.util.AudioUtil;

//A sub that just stays in place
public class StaticSub extends Submarine {

	private double position[];
	private GameEngine currentMap;

	Source distantHit;

	public StaticSub(Activity parentActivity, GameEngine currentMap, int x, int y) {
		super(parentActivity, currentMap);
		this.currentMap = currentMap;
		position = new double[2];
		position[0] = x;
		position[1] = y;

		SoundEnv env = SoundEnv.getInstance(parentActivity);
		try {
			distantHit = env.addSource(SoundEngine.getSoundManager(parentActivity).getSound("distanthit"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick(long elapsedMillis) {
		//doesn't do anything
	}

	@Override
	public double[] getPosition() {
		return position;
	}

	@Override
	public double getRadius() {
		return 5.0;
	}

	@Override
	public boolean resolveCollision(PhysObj other) {
		return this.resolveCollision(other, 0, 0);
	}

	@Override
	protected void setX(double x) {
		position[0] = x;

	}

	@Override
	protected void setY(double y) {
		position[1] = y;
	}

	@Override
	protected boolean resolveBulletHit(Bullet bullet) {
		Log.i("StaticSub", "Static sub hit!");
		//Do damage
		this.setHealth(this.getHealth() - bullet.getDamage());

		//Play distant hit sound from perspective of player sub
		ControlledSubmarine player = currentMap.getPlayerSub();
		double x = bullet.getPosition()[0] - player.getPosition()[0];
		double y = bullet.getPosition()[1] - player.getPosition()[1];
		double distance = Math.sqrt(x*x + y*y);

		distantHit.setPosition((float)x, (float)y, 0);
		distantHit.playDelayed(false,Math.round(((distance / AudioUtil.SOUND_METERS_PER_SECOND))) * 1000);

		return this.getHealth() > 0;
	}

}
*/
