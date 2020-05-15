/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author Bob
 */
public class InteractionManager implements ActionListener {

    private InputManager inputManager;
    private boolean      left, right, jump;
    
    public InteractionManager(InputManager inputManager) {
        this.inputManager = inputManager;
        setUpKeys();
    }
    
    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
               
        inputManager.addListener(this, "Right");        
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Jump"); 
    } 

    public boolean getJump() {
        return jump;
    }
    
    public boolean getLeft() {
        return left;
    }
    
    public boolean getRight() {
        return right;
    }
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        
        if (name.equals("Jump")) {
            jump = isPressed;
        }
        
        if (name.equals("Left")) {
            left = isPressed;
        }

        if (name.equals("Right")) {
            right = isPressed;
        }        
        
    }
    
    
    
}
