/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vr;

import android.vr.input.AndroidDisplay;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import vr.input.DummyDisplay;
import vr.state.VRAppState;

/**
 *
 * @author reden
 */
public class StereoTest extends SimpleApplication {

    public static void main(String[] args){
        StereoTest stereoTest = new StereoTest();
        stereoTest.start();
    }
    private Spatial observer;
    private Material mat;
    
    @Override
    public void simpleInitApp() {
        DummyDisplay display = new DummyDisplay();
        VRAppState vrAppState = new VRAppState(display);
        stateManager.attach(vrAppState);
        
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        
        rootNode.attachChild(geom);
        
        observer = new Node("");
        observer.addControl(vrAppState.getCameraControl());
        rootNode.attachChild(observer);
        
        generateAlignmentCoords();
    }
    
    private void generateAlignmentCoords() {
        float distance = 10;
        for (int x = 0; x < 35; x++) {
            float cos = FastMath.cos(x * FastMath.PI / 16f) * distance;
            float sin = FastMath.sin(x * FastMath.PI / 16f) * distance;
            Vector3f loc = new Vector3f(cos, 0, sin);
            addBox(loc);
            loc = new Vector3f(0, cos, sin);
            addBox(loc);
        }

    }

    private void addBox(Vector3f location) {
        Box b = new Box(0.05f, 0.05f, 0.05f);

        Geometry leftQuad = new Geometry("Box", b);
        leftQuad.rotate(0.5f, 0, 0);



        leftQuad.setMaterial(mat);
        leftQuad.setLocalTranslation(location);
        rootNode.attachChild(leftQuad);
    }
    
}
