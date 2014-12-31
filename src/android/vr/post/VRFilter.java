/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package android.vr.post;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

/**
 *
 * @author reden
 */
public class VRFilter extends Filter {

    @Override
    protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort vp, int w, int h) {
        material = new Material(manager, "MatDefs/BarrelDistortion.j3md");
        float aperture = 178.0f;
        float apertureHalf = 0.5f * aperture * (FastMath.PI / 180.0f);
        float maxFactor = FastMath.sin(apertureHalf);
        material.setFloat("MaxFactor", maxFactor);
    }

    @Override
    protected Material getMaterial() {
        return material;
    }
    
}
