/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;

/**
 *
 * @author Bob
 */
public class GameManager {
    
    private final SceneManager  sceneManager;
    private final PlayerManager playerManager;
    private final GuiManager    guiManager;
    
    
    public GameManager(SimpleApplication app) {
    
        sceneManager  = new SceneManager(app.getRootNode(), app.getAssetManager());
        playerManager = new PlayerManager(app.getRootNode(), app.getInputManager(), app.getAssetManager(), app.getCamera());
        guiManager    = new GuiManager(playerManager.getPlayer(), app.getAssetManager(), app.getGuiNode());
        sceneManager.setPlayer(playerManager.getPlayer());
        
    }
    
    public void update(float tpf) {
        sceneManager.update(tpf);
        playerManager.update(tpf);
        guiManager.update(tpf);
    }
    
}
