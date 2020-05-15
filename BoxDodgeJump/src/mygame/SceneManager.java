/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Bob
 */
public class SceneManager {
    
    private final Node         scene;
    private final Node         walls;
    private final Node         floor ;
    private final AssetManager assetManager;
    private final float        floorWidth;
    private final float        floorLength;
    
    private Player player;
    
    public SceneManager(Node rootNode, AssetManager assetManager) {
        
        this.assetManager = assetManager;
        scene             = new Node();
        walls             = new Node("Walls");
        floor             = new Node("Floor");
        floorWidth        = 60;
        floorLength       = 40;
        initGround();
        Node w1 = initWall(10,  5, 0);
        Node w2 = initWall(55,  5, 5);
        Node w3 = initWall(100, 5, -5);
        rootNode.attachChild(scene);
        scene.attachChild(floor );
        scene.attachChild(walls);
        
        walls.attachChild(w1); 
        walls.attachChild(w2); 
        walls.attachChild(w3);
        
    }
    
    public void setPlayer(Player player) {
        this.player       = player;
    }
    
    private void initGround() {
        
        Box b = new Box(floorWidth/2, 1, floorLength/2);
        Geometry geom1 = new Geometry("Box", b);             
        
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Green);
        geom1.setMaterial(mat1);      
        
        Box c = new Box(floorWidth/2, 1, floorLength/2);
        Geometry geom2 = new Geometry("Box", c);
        
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Cyan);
        geom2.setMaterial(mat2);   
        
        Box d = new Box(floorWidth/2, 1, floorLength/2);
        Geometry geom3 = new Geometry("Box", d);
        
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Yellow);
        geom3.setMaterial(mat3);         
        
        geom1.setLocalTranslation(0, -5, -10);
        geom2.setLocalTranslation(0, -5, -55);
        geom3.setLocalTranslation(0, -5, -100);  
        
        floor.attachChild(geom1);
        floor.attachChild(geom2);
        floor.attachChild(geom3);
        
    }
    
    private Node initWall(float distance, float gap, float offset) {

        float wallWidth = floorWidth/2 - gap/2;
        
        float leftWall  = wallWidth-offset;
        float rightWall = wallWidth+offset; 
        
        Node wall = new Node();
        
        Box b = new Box(1f, 1, 1);
        Geometry geom1 = new Geometry("Box", b);             
        
        Box c = new Box(1, 1, 1);
        Geometry geom2 = new Geometry("Box", c);      
        
        geom1.scale(leftWall/2,5,1);
        geom2.scale(rightWall/2,5,1);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        mat1.setColor("Color", ColorRGBA.Blue);
        
        geom1.setMaterial(mat);            
        geom2.setMaterial(mat1);              
        
        geom1.setLocalTranslation(-floorWidth/2+leftWall/2, 0, 0);
        geom2.setLocalTranslation(floorWidth/2-rightWall/2, 0, 0);        
        
        wall.setLocalTranslation(0, 0, -distance);      
        
        wall.attachChild(geom1);
        wall.attachChild(geom2);
        
        return wall;
        
    }
    
    private void resetWall(Node wall) {
        
    }
    
    public Node getScene() {
        return scene;
    }
    
    public Node getObstacles() {
        return walls;
    }
    
    private void moveGround(float tpf) {
        
        int score = player.getScore();
        
        for (int i = 0; i < floor .getChildren().size(); i++) {
            
            Spatial  cg = floor .getChild(i);
            Vector3f cs = cg.getLocalTranslation();
            
            cg.move(0, 0, (15+score)*tpf);
            
            if (cs.z > 25) {
                cg.setLocalTranslation(cs.x, cs.y, -110);
                score+=1;
                player.setScore(score);
            }
            
        }
        
    }
    
    private void moveWalls(float tpf) {
        
        int score = player.getScore();
        
        for (int i = 0; i < walls.getChildren().size(); i++) {
            
            Spatial  cw = walls.getChild(i);
            Vector3f cs = cw.getLocalTranslation();
            
            cw.move(0, 0, (15+score)*tpf);
            
            if (cs.z > 25) {
                cw.setLocalTranslation(cs.x, cs.y, -110);
            }            
            
        }
        
    }
    
    public void update(float tpf) {
        if (player.isDead()) return;
        moveGround(tpf);
        moveWalls(tpf);
    }
    
}
