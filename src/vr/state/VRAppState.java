/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vr.state;

import android.vr.input.AndroidDisplay;
import android.vr.post.AndroidBarrelDistortionFilter;
import vr.control.StereoCameraControl;
import vr.post.BarrelDistortionFilter;
import vr.input.HeadMountedDisplay;
import vr.post.VRFilter;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.Filter;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;

/**
 *
 * @author reden
 */
public class VRAppState extends AbstractAppState{

    private SimpleApplication app;
    private FilterPostProcessor postProcessorLeft, postProcessorRight;
    private Filter filterLeft, filterRight;
    Camera camLeft,camRight;
    ViewPort viewPortLeft, viewPortRight;
    private StereoCameraControl camControl = new StereoCameraControl();
    private HeadMountedDisplay hmd;
    
    public VRAppState(HeadMountedDisplay hmd){
        this.hmd = hmd;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        
        viewPortLeft = app.getViewPort();
        hmd.initialize();
        camControl.setHeadMountedDisplay(hmd);
        camLeft = app.getCamera();
        setupFiltersAndViews();
        
        float offset = hmd.getHeadMountedDisplayData().getEyeOffset();
        camControl.setCameraOffset(offset);
        
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }
    
    
    
    private void setupFiltersAndViews() {
        camControl.setCamera(camLeft);
        
        camRight = camLeft.clone();
        camControl.setCamera2(camRight);
        
        viewPortRight = app.getRenderManager().createPreView("Right viewport", camRight);
        viewPortRight.setClearFlags(true, true, true);
        viewPortRight.attachScene(this.app.getRootNode());

        camLeft.setViewPort(0.0f, 0.5f, 0.0f, 1.0f);
        camRight.setViewPort(0.5f, 1f, 0.0f, 1f);
        
        if(hmd instanceof AndroidDisplay){
            filterLeft=new AndroidBarrelDistortionFilter(hmd.getHeadMountedDisplayData(), true);
            filterRight =new AndroidBarrelDistortionFilter(hmd.getHeadMountedDisplayData(), false);
        } else {
            filterLeft=new BarrelDistortionFilter(hmd.getHeadMountedDisplayData(), true);
            filterRight =new BarrelDistortionFilter(hmd.getHeadMountedDisplayData(), false);
        }
        
        postProcessorRight =new FilterPostProcessor(app.getAssetManager());               
        postProcessorLeft =new FilterPostProcessor(app.getAssetManager());
        
        viewPortLeft.addProcessor(postProcessorLeft);
        viewPortRight.addProcessor(postProcessorRight);
        
        postProcessorLeft.addFilter(filterLeft);
        postProcessorRight.addFilter(filterRight);
        
        
        
    }
    
    public StereoCameraControl getCameraControl(){
        return camControl;
    }
}
