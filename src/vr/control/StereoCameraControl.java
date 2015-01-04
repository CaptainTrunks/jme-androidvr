/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package vr.control;

import vr.input.HeadMountedDisplay;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.control.Control;
import com.jme3.util.TempVars;
import java.io.IOException;

/**
 * This Control maintains a reference to a Camera,
 * which will be synched with the position (worldTranslation)
 * of the current spatial.
 * @author tim, reden
 */
public class StereoCameraControl extends CameraControl {

    private Quaternion lookDirection = new Quaternion();
    protected Camera camera2;
    private Vector3f cameraOffset = new Vector3f();
    private Vector3f tempf = new Vector3f();
    private HeadMountedDisplay hmd;

    public StereoCameraControl(){
        super();
    }
    
    public StereoCameraControl(Camera camera, Camera camera2) {
        super(camera);
        this.camera2 = camera2;
    }
    
    public StereoCameraControl(Camera camera, Camera camera2, ControlDirection controlDir) {
        super(camera, controlDir);
        this.camera2 = camera2;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        TempVars vars = TempVars.get();
        vars.quat1.set(Quaternion.ZERO);
        vars.vect1.set(Vector3f.ZERO);
        vars.vect2.set(Vector3f.ZERO);
        vars.vect3.set(Vector3f.ZERO);
        vars.vect4.set(Vector3f.ZERO);
        
        Camera camera = getCamera();

        lookDirection.set(hmd.getOrientation());                    
        vars.quat1.set(spatial.getWorldRotation()).multLocal(lookDirection);
        
        
        spatial.getWorldRotation().multLocal(vars.vect1);
        vars.vect1.addLocal(spatial.getWorldTranslation());
        
        camera.setRotation(vars.quat1);
        vars.vect3.set(camera.getRotation().mult(cameraOffset, vars.vect2)).addLocal(vars.vect1);
        camera.setLocation(vars.vect3);

        // negate cameraOffset
        camera2.setRotation(camera.getRotation());
        vars.vect4.set(camera.getRotation().mult(cameraOffset.negate(), vars.vect2)).addLocal(vars.vect1);
        camera2.setLocation(vars.vect4);

        vars.quat1.set(Quaternion.ZERO);
        vars.vect1.set(Vector3f.ZERO);
        vars.vect2.set(Vector3f.ZERO);
        vars.vect3.set(Vector3f.ZERO);
        vars.vect4.set(Vector3f.ZERO);
        vars.release();
    }
    
    public Quaternion getLookDirection() {
        return getCamera().getRotation();
    }
    
    public Vector3f getForwardDirection() {
        return getLookDirection().getRotationColumn(2, tempf);
    }

    private static final String CONTROL_DIR_NAME = "controlDir";
    private static final String CAMERA_NAME = "camera";
   
    public void setCameraOffset(float camOffset) {
        cameraOffset.setX(camOffset);
    }

    public Camera getCamera2(){
        return camera2;
    }
    
    public void setCamera2(Camera cam2){
        this.camera2 = cam2;
    }
    
    @Override
    public Control cloneForSpatial(Spatial newSpatial) {
        StereoCameraControl control = new StereoCameraControl(getCamera(), camera2, getControlDir());
        control.setSpatial(newSpatial);
        control.setEnabled(isEnabled());
        return control;
    }
    
    public void setHeadMountedDisplay(HeadMountedDisplay hmd){
        this.hmd = hmd;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        setControlDir(ic.readEnum(CONTROL_DIR_NAME, ControlDirection.class, ControlDirection.SpatialToCamera));
        setCamera((Camera)ic.readSavable(CAMERA_NAME, null));
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(getControlDir(), CONTROL_DIR_NAME, ControlDirection.SpatialToCamera);
        oc.write(getCamera(), CAMERA_NAME, null);
    }
    
}