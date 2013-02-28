package com.kwhipke.blindsub.submarine.control;

import com.kwhipke.blindsub.ContextUtil;
import com.kwhipke.blindsub.submarine.control.event.OnSteeringChanged;
import com.kwhipke.blindsub.util.PhysicsUtil;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Class which reads the sensor data from the android phone at a specified interval and invokes a callback when the
 * data is read. 
 * @author Kyle
 *
 */
public class SteeringSensorReader {

	
	private SteeringSensorVectors steeringSensorVectors;
	private OnSteeringChanged onSteeringChanged;
	
	public SteeringSensorReader() {
        SensorEventListener gravityListener = new SensorEventListener() {
        	@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {

			}

			@Override
			public void onSensorChanged(SensorEvent arg0) {
				updateGravityVector(arg0.values);
			}
        };
        
        SensorEventListener geoListener = new SensorEventListener() {
        	@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {

			}

			@Override
			public void onSensorChanged(SensorEvent arg0) {
				updateGeoVector(arg0.values);
			}
        };
        
		SensorManager sensorManager = (SensorManager)ContextUtil.getAppContext().getSystemService(Context.SENSOR_SERVICE);
		Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor geoSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(gravityListener, gravitySensor,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(geoListener, geoSensor,SensorManager.SENSOR_DELAY_GAME);
	}
	
	/**
	 * 
	 * @param onSteeringChanged set the callback to be invoked when the steering information is updated based on
	 * new sensor readings;
	 */
	public synchronized void setSteeringChangedCallback(OnSteeringChanged onSteeringChanged) {
		this.onSteeringChanged = onSteeringChanged;
	}
	
	synchronized private void updateGravityVector(float[] newGravity) {
		steeringSensorVectors.updateGravity(newGravity);
		onReadingsChanged();
	}
	
	synchronized private void updateGeoVector(float[] newGeo) {
		steeringSensorVectors.updateGeo(newGeo);
		onReadingsChanged();
	}
	
	private void onReadingsChanged() {
		if (onSteeringChanged != null) {
			onSteeringChanged.onSteeringChanged(steeringSensorVectors.computeSteering());
		}
	}
	
}
