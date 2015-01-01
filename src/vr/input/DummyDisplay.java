/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vr.input;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author reden
 */
public class DummyDisplay implements HeadMountedDisplay{

    private Quaternion orientation = new Quaternion();
    private HeadMountedDisplayData hmdData;
    
    public boolean initialize() {
        hmdData = new HeadMountedDisplayData();
        hmdData.setDisplayWidth(4f);
        hmdData.setDisplayHeight(2f);
        hmdData.setResolutionHeight(1600);
        hmdData.setResolutionHeight(900);
        return true;
    }

    public boolean destroy() {
        return true;
    }

    public Quaternion getOrientation() {
        return orientation;
    }

    public Vector3f getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public HeadMountedDisplayData getHeadMountedDisplayData() {
        return hmdData;
    }
    
}
