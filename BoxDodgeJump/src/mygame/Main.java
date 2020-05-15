package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    GameManager g;
        
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
    }

    @Override
    public void simpleInitApp() {

        flyCam.setEnabled(false);
        g = new GameManager(this);
        FilterPostProcessor processor 
                = (FilterPostProcessor) assetManager.loadAsset("Shaders/Filter.j3f");
        viewPort.addProcessor(processor);  
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        g.update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
