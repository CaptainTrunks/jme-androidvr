/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vr.post;

/**
 *
 * @author reden
 */

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix4f;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.post.Filter;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.FrameBuffer;
import vr.input.HeadMountedDisplayData;
/**
 *
 * @author reden
 */

public class BarrelDistortionFilter extends Filter{

    private HeadMountedDisplayData hmdData;
    private boolean isLeft = true;
    private float scaleFactor = 0.93f;
    
    public BarrelDistortionFilter(HeadMountedDisplayData hmdData, boolean isLeft){
        this.hmdData = hmdData;
        this.isLeft = isLeft;
    }
    
    @Override
    protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort vp, int width, int height) {
        material = new Material(manager, "MatDefs/BarrelDistortion.j3md");
        float aspectRatio = (float)(width) / (float)height;

        float halfScreenDistance = (hmdData.getDisplayHeight() * 0.5f);
        float yfov = 1.0f * FastMath.atan(halfScreenDistance/hmdData.getEyeToScreenDistance());

        vp.getCamera().setFrustumPerspective(FastMath.RAD_TO_DEG * yfov, aspectRatio, vp.getCamera().getFrustumNear(), vp.getCamera().getFrustumFar());
        float viewCenter = hmdData.getDisplayWidth() * 0.5f;

        float eyeProjectionShift = viewCenter - hmdData.getLensSeparationDistance() * 0.5f;
        float projectionCenterOffset = 4f * eyeProjectionShift / width;
        if(!isLeft){
            projectionCenterOffset = - projectionCenterOffset;
        }
        Vector2f lensCenter = new Vector2f(0.5f + projectionCenterOffset, 0.5f);
        
        Matrix4f mat = new Matrix4f();
        mat.setTranslation(projectionCenterOffset, 0 ,0);
        mat.multLocal(vp.getCamera().getProjectionMatrix());
        vp.getCamera().setProjectionMatrix(mat);
        
        float[] distortion = hmdData.getDistortion();
        Vector2f screenCenter = new Vector2f(0.5f, 0.5f);
        Vector2f scale = new Vector2f(scaleFactor, scaleFactor * aspectRatio );
        Vector2f scaleIn = new Vector2f(1f, 1f / aspectRatio );
        material.setVector2("LensCenter", lensCenter);
        material.setVector2("ScreenCenter", screenCenter);
        material.setVector2("Scale", scale);
        material.setVector2("ScaleIn", scaleIn);

        material.setVector4("HmdWarpParam", new Vector4f(distortion[0], distortion[1], distortion[2], distortion[3]));
    }

    @Override
    protected Material getMaterial() {
        return material;
    }

}
