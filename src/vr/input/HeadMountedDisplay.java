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
public interface HeadMountedDisplay {
    
    public boolean initialize();
    
    public boolean destroy();
    
    public Quaternion getOrientation();
    
    public Vector3f getPosition();
    
    public HeadMountedDisplayData getHeadMountedDisplayData();
}
