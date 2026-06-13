/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstsketch;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author 345954069
 */
public class MySketch extends PApplet{
    private PlayerSprite player;
    private Person ramNPC;
    private Throw mountain;
    private Person demonBoss;
    private int bossDirectionY = 1;
    private int bossSpeed = 20;
    private int bossHitSpeed = 10;
    int stage = 0;
    private boolean isCarryingMountain = false;
    private PImage bg;
    private PImage characterSelect;
    private PImage bgStage1;
    private PImage bossroom;
    private PImage lairbg;
    private int dialogueStep = 0;
    private PImage dialogue1;
    private PImage dialogue2;
    private PImage emptyDialogue;
    private ArrayList<Throw> projectiles = new ArrayList<Throw>();
    private ArrayList<Throw> bossAttacks = new ArrayList<Throw>();
    
    private ArrayList<String> dialogueLines = new ArrayList<String>(); // Use an ArrayList for dynamic loading
    
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
        demonBoss = new Person(this, 500, 160, "images/demonResize.png");
        lairbg = loadImage("images/demonlair.jpg");
        lairbg.resize(700,0);
        dialogue1 = loadImage("images/dialoguePic1.png");
        dialogue1.resize(700, 0);
        dialogue2 = loadImage("images/dialoguePic2.png");
        dialogue2.resize(700, 0);
        emptyDialogue = loadImage("images/emptyDialogue.png"); //image for the text
        emptyDialogue.resize(700,0);
        
        loadDialogueFile(); //do the fileIO inside a method
        
    }
    
    public void loadDialogueFile() {
        try {
            File file = new File(dataPath("dialogue.txt"));
            Scanner output  = new Scanner(file);
            
            while(output.hasNextLine()) { //keep reading next line
                String line = output.nextLine(); 
                dialogueLines.add(line); //add teh lines to the array list
            }
            output.close(); //close scanner
        } catch (FileNotFoundException e) {
            System.out.println("Error: Can't find the file at" + dataPath("dialogue.txt"));
            
        }
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
                image(emptyDialogue, 0,265); //draw the dialogue box
                
                fill(255);
                textSize(26);
                textAlign(LEFT,TOP);
                
                if (!dialogueLines.isEmpty() && dialogueStep < dialogueLines.size()) {
                    // Setting a bounding box (x, y, width, height) lets the text auto-wrap inside the box
                    text(dialogueLines.get(dialogueStep), 40, 285, 620, 100);
                }
            } else {
                dialogueStep = 0;
            }

            //If player goes all the way to the right side of current room
            if (player.x > width) {
                fill(255, 0, 0);
                stage = 2;
                player.x = 0; // Moves player to the left side of new room
            }
                        
        } else if (stage == 2) {
            fill(0);
            image(bossroom, 0,0, width, height); //loads new room bg
            player.draw();
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
            
            
            // MOUNTAIN COLLISION & CARRY MECHANIC
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
            
            //If player goes all the way to the left side of current room
            if (player.x < 0) {
                fill(255, 0, 0);
                stage = 1;
                player.x = width; // Moves player to the right side of new room
            }
            
            //room of stage 3
            if (player.x > width) {
                fill(255, 0, 0);
                stage = 3;
            
            // If carrying the mountain, calculate its new position relative to the player before resetting player.x
            if (isCarryingMountain) {
                // Maintain the horizontal snap math you used earlier
                mountain.x = 0 + (mountain.image.width / 2) - (player.getImage().width / 2);
                // Keep the vertical snap position intact
                mountain.y = player.y - mountain.image.height + 30; 
            }

            player.x = 0; // Moves player to the left side of new room
            }
            
        } else if (stage == 3) {
            fill(0);
            image(lairbg, 0,0, width, height); //loads new room bg
            
            //BOSS MOVEMENT
            
            // boss y position
            float bossY = demonBoss.y +(bossSpeed * bossDirectionY);
            //check screen bounds
            if (bossY < 0 || bossY > height - 200) { 
                bossDirectionY *= -1; //reverse boss direction if reach ends of screen
            }
            //auto movement of boss
            demonBoss.move(0, (int)(bossSpeed * bossDirectionY));
            
            //BOSS ATTACKS
            if (frameCount % 60 == 0) { //for each 60 frames, one projectile is thrown
                //spawn projectile form boss current posiiton
                Throw bossAttack = new Throw(this, demonBoss.x, demonBoss.y, "images/demonFireball.png");
                bossAttacks.add(bossAttack);
            }
            
            for (int i = bossAttacks.size() - 1; i>=0; i--) {
                Throw ba = bossAttacks.get(i);
                
                ba.x -= bossHitSpeed; //speed at which boss projectiles goes left
                
                ba.draw();
                
                if (player.isCollidingWith((Object) ba)) {
                player.playerHealth -= 10; //reduce player health by 10
                bossAttacks.remove(i);
                System.out.println("Player Hit!!" + player.playerHealth);
                continue;
                }
                
                //remove boss attacks if missed and off screen
                if (ba.x < -10) {
                    bossAttacks.remove(i);
                }  
            }
            
            player.draw();
            demonBoss.draw();
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
            
            
            // MOUNTAIN COLLISION & CARRY MECHANIC
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
                if (dialogueStep < dialogueLines.size() - 1) { //stop the index from going out of bounds using the size()
                    dialogueStep++;
                }
            }
        } else if (stage == 2 || stage == 3) {
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
            
            //Check collision with the Boss if we are on Stage 3
            if (stage == 3 && demonBoss.isCollidingWith(p)) { //if boss collided with projectile
                System.out.println("Boss hit");
                projectiles.remove(i);
            }

            
            
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
