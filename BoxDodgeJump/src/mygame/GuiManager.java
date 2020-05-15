/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Bob
 */
public class GuiManager {
    
    private Player     player;
    private BitmapText scoreDisplay;
    
    public GuiManager(Player player, AssetManager assetManager, Node guiNode) {
        this.player     = player;
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        scoreDisplay    = new BitmapText(font, false);
        scoreDisplay.setText("Player Score: " + player.getScore());
        guiNode.attachChild(scoreDisplay);
        scoreDisplay.setLocalTranslation(Display.getWidth()-scoreDisplay.getLineWidth()*1.1f, Display.getHeight()-scoreDisplay.getLineHeight(),0);
    }
    
    public void update(float tpf) {
        scoreDisplay.setText("Player Score: " + player.getScore());
    }
    
}
