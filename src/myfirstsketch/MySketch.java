/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstsketch;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author 345954069
 */
public class MySketch extends PApplet{
    private PlayerSprite player;
    private Person ramNPC;
    private Person demonBoss;
    int stage = 0;
    private PImage bg;
    private PImage characterSelect;
    private PImage bgStage1;
    private PImage bossroom;
    private int dialogueStep = 0;
    private PImage dialogue1;
    private PImage dialogue2;
    
    
    public void settings() {
        size(700,400);
    }
    
    public void setup() {
        background(255);
        player = new PlayerSprite (this, 200, 200, 0, "images/monkeykingidle.png", new Throw(199, 199));
        bg = loadImage("images/mainmenubg.png");
        bgStage1 = loadImage("images/stage1_2.jpg");
        bossroom = loadImage("images/bossroom.jpg");
        characterSelect = loadImage("images/menuscreenking.png");
        ramNPC = new Person(this, 100, 160, "images/ram2.png", new Throw(199, 199));
        demonBoss = new Person(this, 100, 160, "images/demonking.png", new Throw(199, 199));
        dialogue1 = loadImage("images/dialoguePic1.png");
        dialogue1.resize(700, 0);
        dialogue2 = loadImage("images/dialoguePic2.png");
        dialogue2.resize(700, 0);
        
    }
    
    public void draw() {
        image(bg, 0, 0, width, height);
       
        
        if (stage == 0) {
            textSize(30);
            image(characterSelect, 120, 30, 450, 397);
            fill(0);
            text("My Cultural Story", 240,50);
            text("Press ENTER to continue", 190, 100); 
            
        } else if (stage == 1) {
            textSize(20);
            fill(0);
            image(bgStage1, 0,0, width, height);
            ramNPC.draw();
            player.draw();
            
            if (keyPressed) {
                if (keyCode == LEFT) {
                    player.move(-player.getSpeed(),0);
                } else if (keyCode == RIGHT) {
                    player.move(player.getSpeed(),0);
                } else if (keyCode == UP) {
                    player.move(0, -player.getSpeed());
                } else if (keyCode == DOWN) {
                    player.move(0, player.getSpeed());
                }
                
            }
            
            if (player.isCollidingWith(ramNPC)) {
                fill(255, 0, 0);
                
                if (dialogueStep == 0) {
                    image(dialogue1,0,265);
                } else if (dialogueStep == 1) {
                    image(dialogue2,0,265);
                }
            } else {
                dialogueStep = 0; //reset dialogue if player walks away
            }
            
            if (player.x > width) {
                fill(255, 0, 0);
                stage = 2;
                player.x = 0; // Moves player to the left side of new room
            }
            
        } else if (stage == 2) {
            fill(0);
            image(bossroom, 0,0, width, height); //loads new room bg
            player.draw();
            demonBoss.draw();
            
            //movement
            if (keyPressed) {
                if (keyCode == LEFT) {
                    player.move(-player.getSpeed(),0);
                } else if (keyCode == RIGHT) {
                    player.move(player.getSpeed(),0);
                } else if (keyCode == UP) {
                    player.move(0, -player.getSpeed());
                } else if (keyCode == DOWN) {
                    player.move(0, player.getSpeed());
                }
                
            }
            
            if (player.x < 0) {
                fill(255, 0, 0);
                stage = 1;
                player.x = width; // Moves player to the right side of new room
            }
        }
    }
            
    public void keyPressed() {
        if (stage == 0) {
            if (keyCode == ENTER) {
                stage = 1;
            }
        }
        
        if (stage == 1) {
            if (key == ENTER && player.isCollidingWith(ramNPC)) {
                if (dialogueStep < 1) {
                    dialogueStep++;
                } 
            }
        } else if (stage == 2) {
            if (key == ENTER && player.isCollidingWith(ramNPC)) {
                if (dialogueStep < 1) {
                    dialogueStep++;
                } 
            }
        }
    }
    
    public void mousePressed() {
        System.out.println("x: " + mouseX + " y: "  + mouseY);
    }
    
    }
