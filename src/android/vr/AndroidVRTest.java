/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package android.vr;

import android.vr.input.AndroidDisplay;
import com.jme3.app.FlyCamAppState;
import vr.state.VRAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 *
 * @author reden2
 */
public class AndroidVRTest extends SimpleApplication{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AndroidVRTest androidVRTest = new AndroidVRTest();
        androidVRTest.start();
    }
    
    private Spatial observer;
    private Material mat;
    
    @Override
    public void simpleInitApp() {
        AndroidDisplay display = new AndroidDisplay();
        VRAppState vrAppState = new VRAppState(display);
        stateManager.attach(vrAppState);
        
//        Box b = new Box(1, 1, 1);
//        Geometry geom = new Geometry("Box", b);

        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
//        geom.setMaterial(mat);
//        
//        rootNode.attachChild(geom);
        
        observer = new Node("");
        observer.addControl(vrAppState.getCameraControl());
        rootNode.attachChild(observer);
        getRenderManager().removePostView(guiViewPort);
        guiNode.detachAllChildren();
        generateAlignmentCoords();
//        getFlyByCamera().unregisterInput();
//        flyCam.setEnabled(false);
//        stateManager.getState(FlyCamAppState.class).setEnabled(false);
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

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
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
