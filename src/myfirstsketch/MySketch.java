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
    private Person player;
    private Person ramNPC;
    int stage = 0;
    private PImage bg;
    private PImage characterSelect;
    private PImage bgStage1;
    private int dialogueStep = 0;
    
    
    public void settings() {
        size(700,400);
    }
    
    public void setup() {
        background(255);
        player = new Person (this, 200, 200, "images/monkeykingidle.png", new PlayerStats(100, 3), new Throw(199, 199, 9, false));
        bg = loadImage("images/mainmenubg.png");
        bgStage1 = loadImage("images/stage1_2.jpg");
        characterSelect = loadImage("images/menuscreenking.png");
        ramNPC = new Person(this, 100, 190, "images/ram2.png", new PlayerStats(100, 3), new Throw(199, 199, 9, false));
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
                    player.move(-5,0);
                } else if (keyCode == RIGHT) {
                    player.move(5,0);
                } else if (keyCode == UP) {
                    player.move(0, -5);
                } else if (keyCode == DOWN) {
                    player.move(0, 5);
                }
                
            }
            
            if (player.isCollidingWith(ramNPC)) {
                fill(255, 0, 0);
                
                if (dialogueStep == 0) {
                    this.text("Hanuman.. My wife, Sita, has been captured by Demon King Ravana.", ramNPC.x, ramNPC.y);
                } else if (dialogueStep == 1) {
                    this.text("I want you to scout his area so we can go and attack him!", ramNPC.x, ramNPC.y);
                }
            } else {
                dialogueStep = 0; //reset dialogue if player walks away
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
        }
    }
    
    public void mousePressed() {
        System.out.println("x: " + mouseX + " y: "  +mouseY);
    }
    
    }
