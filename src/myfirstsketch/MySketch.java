/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstsketch;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 *
 * @author 345954069
 */
public class MySketch extends PApplet{
    private PlayerSprite player;
    private Person ramNPC;
    private Throw mountain;
    private Person demonBoss;
    int stage = 0;
    private boolean isCarryingMountain = false;
    private PImage bg;
    private PImage characterSelect;
    private PImage bgStage1;
    private PImage bossroom;
    private int dialogueStep = 0;
    private PImage dialogue1;
    private PImage dialogue2;
    private PImage emptyDialogue;
    private ArrayList<Throw> projectiles = new ArrayList<Throw>();
    
    
    public void settings() {
        size(700,400);
    }
    
    public void setup() {
        background(255);
        player = new PlayerSprite (this, 200, 200, 0, "images/monkeykingidle.png");
        mountain = new Throw (this, 342,238, "images/mountainResize.png");
        bg = loadImage("images/mainmenubg.png");
        bgStage1 = loadImage("images/stage1_2.jpg");
        bossroom = loadImage("images/bossroom.jpg");
        characterSelect = loadImage("images/menuscreenking.png");
        ramNPC = new Person(this, 100, 160, "images/ram2.png");
        demonBoss = new Person(this, 100, 160, "images/demonResize.png");
        dialogue1 = loadImage("images/dialoguePic1.png");
        dialogue1.resize(700, 0);
        dialogue2 = loadImage("images/dialoguePic2.png");
        dialogue2.resize(700, 0);
        emptyDialogue = loadImage("images/emptyDialgue.png"); //image for the text
        
        
        
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
            
            // To update and draw projectiles
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                Throw p = projectiles.get(i);
                p.update();
                p.draw();
    
                // Check if it left the screen; if so, remove it
                if (!p.isInScreen()) {
                    projectiles.remove(i);
                }
            }
                        
            drawHealthBar();
            
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
//            demonBoss.draw();
            mountain.draw();
            drawHealthBar();
            
            manageProjectiles();
            
            // movement
            if (keyPressed) {
                int dx = 0;
                int dy = 0;
                
                if (keyCode == LEFT) {
                    dx = -player.getSpeed();
                } else if (keyCode == RIGHT) {
                    dx = player.getSpeed();
                } else if (keyCode == UP) {
                    dy = -player.getSpeed();
                } else if (keyCode == DOWN) {
                    dy = player.getSpeed();
                }
                
                // Move the player using the calculated values
                player.move(dx, dy);
                
                // If the player is carrying the mountain, move the mountain by the same amount
                if (isCarryingMountain) {
                    mountain.move(dx, dy);
                }
            }
            
            
            
            // collision
            // 2. COLLISION & CARRY MECHANIC
            if (!isCarryingMountain) {
                if (player.isCollidingWith((Object) mountain)) {
                    // 1. Vertical Snap: Place player right under the mountain (adjusting for transparent pixels if needed)
                    player.y = mountain.getPosY() + mountain.image.height - 30;

                    // 2. Horizontal Snap: Center the player perfectly along the mountain's width
                    player.x = mountain.getPosX() + (mountain.image.width / 2) - (player.getImage().width / 2);

                    // Activate the carrying lock
                    isCarryingMountain = true; 
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
            if (key == ' ') { //checks spacebar
                // Create a new projectile at the player's X and Y coordinates
                Throw newProjectile = new Throw(this, player.x, player.y, "images/mountainResize.png");
                
                // Add it to our active projectiles list
                projectiles.add(newProjectile);
            }
        }
    }
    
    public void mousePressed() {
        System.out.println("x: " + mouseX + " y: "  + mouseY);
    }
    
    // Helper method to keep our main stage blocks cleanly organized
    public void manageProjectiles() {
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Throw p = projectiles.get(i);
            p.update();
            p.draw();

            // Check if it not on screen anymore, if yes then remove it
            if (!p.isInScreen()) {
                projectiles.remove(i);
            }
        }
    }
    
    
    //HEALTH OF MONKEY KING
    public void drawHealthBar() {
    float barWidth = 200; // Total width of the health bar
    float barHeight = 20; // Height of the health bar
    float x = 20;         // X position on the screen
    float y = 20;         // Y position on the screen
    
    // 1. Calculate the health ratio
    // (Assuming player.health and player.maxHealth exist)
    float healthRatio = (float) player.playerHealth / player.MAX_HEALTH;
    
    // Stop the bar from stretching if health goes out of bounds
    healthRatio = constrain(healthRatio, 0, 1); 

    // 2. Draw the background (Red/Empty bar)
    fill(250, 50, 50);
    noStroke();
    rect(x, y, barWidth, barHeight, 5); // 5 is for slightly rounded corners

    // 3. Draw the foreground (Green/Current health)
    fill(50, 220, 50);
    rect(x, y, barWidth * healthRatio, barHeight, 5);
    
    // Optional: Add a nice dark border around the whole thing
    noFill();
    stroke(0);
    strokeWeight(2);
    rect(x, y, barWidth, barHeight, 5);
    }
    
}
