/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vr.input;

/**
 *
 * @author reden
 */
public class HeadMountedDisplayData {
    
    private float eyeToScreenDistance = 0.05f;
    private float lensSeparationDistance = 0.075f;
    private float eyeOffset = 0.075f;
    private float displayWidth;
    private float displayHeight;
    private int resolutionWidth;
    private int resolutionHeight;
    private float[] distortion = new float[]{1f, 0.22f, 0.24f, 0f};

    public float getEyeToScreenDistance() {
        return eyeToScreenDistance;
    }

    public void setEyeToScreenDistance(float eyeToScreenDistance) {
        this.eyeToScreenDistance = eyeToScreenDistance;
    }

    public float getLensSeparationDistance() {
        return lensSeparationDistance;
    }

    public void setLensSeparationDistance(float lensSeparationDistance) {
        this.lensSeparationDistance = lensSeparationDistance;
    }

    public float[] getDistortion() {
        return distortion;
    }

    public void setDistortion(float[] distortion) {
        this.distortion = distortion;
    }

    public float getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(float displayWidth) {
        this.displayWidth = displayWidth;
    }

    public float getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(float displayHeight) {
        this.displayHeight = displayHeight;
    }
    
    public float getEyeOffset() {
        return eyeOffset;
    }

    public int getResolutionWidth() {
        return resolutionWidth;
    }

    public int getResolutionHeight() {
        return resolutionHeight;
    }

    public void setEyeOffset(float eyeOffset) {
        this.eyeOffset = eyeOffset;
    }

    public void setResolutionWidth(int resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    public void setResolutionHeight(int resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
    }
}
