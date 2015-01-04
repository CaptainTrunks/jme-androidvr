/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package android.vr.input;

import vr.input.HeadMountedDisplay;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.system.android.JmeAndroidSystem;
import vr.input.HeadMountedDisplayData;

/**
 *
 * @author reden
 */
public class AndroidDisplay implements HeadMountedDisplay, SensorEventListener {

    private float[] gravity;
    private float[] geomagnetic;
    
    private float[] orientationVector = new float[3];
    private Quaternion orientation;
    private Quaternion tempQuat = new Quaternion();
    private Sensor accelerometer;
    private Sensor magnetometer;
    private SensorManager sensorManager;
    private HeadMountedDisplayData hmdData;
    
    private float R[] = new float[9];
    private float I[] = new float[9];
    
    private float inchesToMeters = 0.0254f;

    public boolean initialize() {
        orientation = new Quaternion();
        sensorManager = (SensorManager) JmeAndroidSystem.getActivity().getApplication().getSystemService(Activity.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);

        hmdData = new HeadMountedDisplayData();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        JmeAndroidSystem.getActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        float screenHeight = displaymetrics.heightPixels / displaymetrics.ydpi * inchesToMeters;
        float screenWidth = displaymetrics.widthPixels / displaymetrics.xdpi * inchesToMeters;
        hmdData.setDisplayWidth(screenWidth);
        hmdData.setDisplayHeight(screenHeight);
        return true;
    }

    public Quaternion getOrientation() {
        return orientation;
    }

    public Vector3f getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values;
        }
        if (gravity != null && geomagnetic != null) {
            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
            if (success) {
                SensorManager.getOrientation(R, orientationVector);
                tempQuat.fromAngles(orientationVector[2], -orientationVector[1], orientationVector[0]);
                orientation.slerp(tempQuat, 0.2f);
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public boolean destroy() {
        sensorManager.unregisterListener(this);
        return true;
    }
    
    public HeadMountedDisplayData getHeadMountedDisplayData(){
        return hmdData;
    }
}
