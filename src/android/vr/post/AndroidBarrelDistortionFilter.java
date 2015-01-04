/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package android.vr.post;

/**
 *
 * @author reden
 */

import vr.post.*;
import android.opengl.GLES20;
import vr.input.HeadMountedDisplayData;
/**
 *
 * @author reden
 */

public class AndroidBarrelDistortionFilter extends BarrelDistortionFilter{

    public AndroidBarrelDistortionFilter(HeadMountedDisplayData hmdData, boolean isLeft){
        super(hmdData, isLeft);
    }
    
    @Override
    protected void preFrame(float tpf) {
        super.preFrame(tpf); //To change body of generated methods, choose Tools | Templates.
        GLES20.glDepthMask(true);
    }

}
