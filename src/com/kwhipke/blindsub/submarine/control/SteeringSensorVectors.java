package com.kwhipke.blindsub.submarine.control;

import android.content.Context;
import android.hardware.SensorManager;

import com.kwhipke.blindsub.ContextUtil;
import com.kwhipke.blindsub.util.PhysicsUtil;

/**
 * Encapsulates the vectors that are used to determine the steering of the subs steering wheel
 * @author Kyle
 *
 */
public class SteeringSensorVectors {
	private float lastGravityVector[] = new float[3];
	private float lastGeoVector[] = new float[3];
	
	public SteeringSensorVectors(float[] gravityVector, float[] geoVector) {
		this.lastGeoVector = geoVector;
		this.lastGravityVector = gravityVector;
	}

	public void updateGravity(float[] newGravity) {
		this.lastGravityVector = newGravity;		
	}

	public void updateGeo(float[] newGeo) {
		this.lastGeoVector = newGeo;
		
	}

	/**
	 * 
	 * @return the steering target based on the current vectors. Null if no steering could be calculated. 
	 */
	public Steering computeSteering() {
		//Calculates the new steering and invokes the steering changed handler
		SensorManager sensorManager = (SensorManager)ContextUtil.getAppContext().getSystemService(Context.SENSOR_SERVICE);
		float lastOrientation[] = new float[3];
		float lastRotationMatrix[] = new float[9];
		float steeringDegrees;
		if (SensorManager.getRotationMatrix(lastRotationMatrix, null,
               lastGravityVector, lastGeoVector)) {
			SensorManager.getOrientation(lastRotationMatrix, lastOrientation);
			
			//Convert the tilt to degrees
			float roll = PhysicsUtil.radiansToDegrees(lastOrientation[1]);
			//0 to 90 is turning left, 0 to -90 is turning right
			if (roll > 0) {
				steeringDegrees = Math.min(roll, 90);
			} else {
				steeringDegrees = Math.max(roll, -90); 
			}
			
			return new Steering(steeringDegrees / 90);
		}
		return null;
	}
}
