/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Player {
    
    private Node    model;
    private int     score;
    private boolean isJumping;
    private boolean isFalling;
    private boolean isDead;
    
    public Player(Node model) {
        this.model = model;
    }
    
    public void jump() {
        isJumping = true;
    }
    
    public void stopJump() {
        isJumping = false;
    }    
    
    public boolean isJumping() {
        return isJumping;
    }
    
    public Node getModel() {
        return model;
    }
    
    public void fall() {
        isFalling = true;
    }
    
    public boolean isFalling() {
        return isFalling;
    }
    
    public void die() {
        isDead = true;
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void reset() {
        score     = 0;
        isFalling = false;
        isDead    = false;
    }
    
}
