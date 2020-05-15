/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Bob
 */
public class PlayerManager {
    
    private final Player             player;
    private final InteractionManager interactionManager;
    private final Camera             camera;
    private final Node               walls;
    private final Node               floor;
    
    public PlayerManager(Node rootNode, InputManager inputManager, AssetManager assetManager, Camera camera) {
        interactionManager = new InteractionManager(inputManager);
        player             = new Player(createPlayerModel(assetManager));
        this.camera        = camera;
        walls              = (Node) rootNode.getChild("Walls");
        floor              = (Node) rootNode.getChild("Floor");
        player.getModel().setLocalTranslation(new Vector3f(0,-3.0f,0));
        rootNode.attachChild(player.getModel());
    }
    
    private Node createPlayerModel(AssetManager assetManager) {
    
        Node model = new Node();
        Box b = new Box(1, 1, 1); 
        Geometry geom = new Geometry("Box", b);  
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); 
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);       
        model.attachChild(geom);
        return model;
    
    }
    
    public Player getPlayer() {
        return player;
    }
    
    float velocity = 35;
    
    private void updateJump(float tpf) {
        
        Vector3f playerSpot = player.getModel().getLocalTranslation();
        
        if (interactionManager.getJump()) {
            
            if (!player.isJumping() && player.getModel().getLocalTranslation().y == -3.0f) {
                player.jump();
            }
            
        }        
        
        if (player.isJumping()) {
            
            player.getModel().move(0,velocity*tpf,0);
            
            if (velocity > 12) 
                velocity -= velocity*tpf*3;
            else
                velocity = 12;
            
            if (playerSpot.y > 6) {
                player.stopJump();
            }
            
        }
        
        else if (playerSpot.y > -3.0f) {
            player.getModel().move(0,-velocity*tpf,0);
            velocity += velocity*tpf*3;
        }
        
        else if (!player.isFalling()) {
            player.getModel().setLocalTranslation(new Vector3f(playerSpot.x,-3.0f,playerSpot.z));
            velocity = 35;
        }
        
    }
    
    private void movePlayer(float tpf) {
        
        if (player.isDead()) return;
        
        if (interactionManager.getLeft()) {
            player.getModel().move(-10*tpf,0,0);
        }
        
        if (interactionManager.getRight()) {
            player.getModel().move(10*tpf,0,0);
        }
        
    }
    
    private void updateCamera() {
        
        Vector3f playerSpot = player.getModel().getLocalTranslation();
        camera.setLocation(new Vector3f(playerSpot.x, 5,playerSpot.z+15));
        camera.lookAt(playerSpot.clone().setY(1), new Vector3f(0,1,0));
        
    }
    
    private void checkCollision() {
        CollisionResults results = new CollisionResults();
        walls.collideWith(player.getModel().getWorldBound(), results);
        if (results.size() > 0) {
            player.die();
        }
    }
    
    private void checkFloor(float tpf) {
        
        CollisionResults results = new CollisionResults();
        floor.collideWith(player.getModel().getWorldBound(), results);

        if (results.size() == 0) {
            
            if (player.getModel().getLocalTranslation().y <= -3f) {
                player.fall();
                player.die();
            }
            
        }       
        
        if (player.isFalling()) {
            player.getModel().move(0,-15*tpf,0);
        }
        
    }
    
    private void checkReset() {
        
        if (!interactionManager.getJump()) return;
        
        player.getModel().setLocalTranslation(new Vector3f(0,-3.0f,0));
        player.reset();
        
        int z = -10;
        
        for (int i = 0; i < walls.getChildren().size(); i++) {
            
            Vector3f wallSpot = walls.getChild(i).getLocalTranslation();
            walls.getChild(i).setLocalTranslation(wallSpot.x,wallSpot.y,z);
            z-=45;
            
        }
        
        z = -10;
        
        for (int i = 0; i < floor.getChildren().size(); i++) {
            floor.getChild(i).setLocalTranslation(0,-5,z);
            z-=45;
            
        }        
        
    }
    
    public void update(float tpf) {
        
        updateJump(tpf);
        movePlayer(tpf);
        updateCamera();
        checkCollision();
        checkFloor(tpf);
        
        if (player.isDead()) 
            checkReset();
        
    }
    
}
