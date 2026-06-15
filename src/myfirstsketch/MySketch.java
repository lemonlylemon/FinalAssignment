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
    private Person sitaNPC;
    private Throw mountain;
    private Person demonBoss;
    private int bossDirectionY = 1;
    private int bossSpeed = 20;
    int stage = 0;
    private int bossHealth = 500;
    private int BOSS_MAX_HEALTH = 500;
    private boolean isCarryingMountain = false;
    private PImage bg;
    private PImage characterSelect;
    private PImage bgStage1;
    private PImage bossroom;
    private PImage lairbg;
    private PImage dungeonbg;
    private PImage endScreen;
    
    private int dialogueStep = 0;
    private int dialogueStage2Step = 0;
    private int dialogueStage3Step = 0;
    private int dialogueStage4Step = 0;
    
    private PImage dialogue1;
    private PImage dialogue2;
    private PImage emptyDialogue;
    private PImage gameOver;
    private ArrayList<Throw> projectiles = new ArrayList<Throw>();
    private ArrayList<Throw> bossAttacks = new ArrayList<Throw>();
    
    private ArrayList<String> dialogueLines = new ArrayList<String>(); // Use an ArrayList for dynamic loading
    private ArrayList<String> dialogueStage2Lines = new ArrayList<String>(); // Use an ArrayList for dynamic loading
    private ArrayList<String> dialogueStage3Lines = new ArrayList<String>(); // Use an ArrayList for dynamic loading
    private ArrayList<String> dialogueStage4Lines = new ArrayList<String>(); // Use an ArrayList for dynamic loading

    private boolean bossDoneDialogue = false;
    
    //boss attack pattern: 1 = spawn fireball, 0 = safe area
    private int [][] bossPattern = {
        {1, 0, 1}, 
        {1, 1, 0},
        {0, 1, 1}
    };

    
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
        sitaNPC = new Person(this, 500, 160, "images/sitaResize.png");
        demonBoss = new Person(this, 500, 20, "images/demonResizes.png");
        lairbg = loadImage("images/demonlair.jpg");
        lairbg.resize(700,0);
        dialogue1 = loadImage("images/dialoguePic1.png");
        dialogue1.resize(700, 0);
        dialogue2 = loadImage("images/dialoguePic2.png");
        dialogue2.resize(700, 0);
        emptyDialogue = loadImage("images/emptyDialogue.png"); //image for the text
        emptyDialogue.resize(700,0);
        gameOver = loadImage("images/gameOver.jpg");
        gameOver.resize(700,0);
        dungeonbg = loadImage("images/dungeon.png");
        dungeonbg.resize(700, 0);
        endScreen = loadImage("images/endScreen.jpg");
        endScreen.resize(700, 0);
        
        loadDialogueFile(dialogueLines, "dialogue.txt"); //do the fileIO inside a method
        loadDialogueFile(dialogueStage2Lines, "dialogue2.txt");
        loadDialogueFile(dialogueStage3Lines, "dialogue3.txt");
        loadDialogueFile(dialogueStage4Lines, "dialogue4.txt");

        
    }
    
    public void loadDialogueFile(ArrayList list, String filename) {
        try {
            File file = new File(dataPath(filename));
            Scanner output  = new Scanner(file);
            
            while(output.hasNextLine()) { //keep reading next line
                String line = output.nextLine(); 
                list.add(line); //add the lines to the array list
                System.out.println(line);
            }
            output.close(); //close scanner
        } catch (FileNotFoundException e) {
            System.out.println("Error: Can't find the file at" + dataPath(filename));
            
        }
    }
    
    public void draw() {
        image(bg, 0, 0, width, height);
       
        
        if (stage == 0) {
            textSize(30);
            image(characterSelect, 120, 30, 450, 397);
            textAlign(CENTER, CENTER);
            fill(0);
            text("My Cultural Story", width/2,50);
            text("Press ENTER to continue", width/2, 100); 
            
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
                    if (player.x > 0) {
                        player.move(-player.getSpeed(),0);
                    }
                } else if (keyCode == RIGHT) {
                    player.move(player.getSpeed(),0);
                } else if (keyCode == UP) {
                    if (player.y > 0) {
                        player.move(0, -player.getSpeed());
                    }
                } else if (keyCode == DOWN) {
                    if (player.y < height - player.getImage().height) {
                        player.move(0, player.getSpeed());
                    }
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
            
            //check if player is touching mountian but not picked it up yet
            boolean touchingMountain = player.isCollidingWith((Object) mountain) && !isCarryingMountain;
            
            // movement
            if (keyPressed && !touchingMountain) {
                int dx = 0;
                int dy = 0;
                
                if (keyCode == LEFT) {
                    if (player.x > 0) {
                        dx = -player.getSpeed();
                    }
                } else if (keyCode == RIGHT) {
                    dx = player.getSpeed();
                } else if (keyCode == UP) {
                    if (player.y > 0) {
                        dy = -player.getSpeed();
                    }
                } else if (keyCode == DOWN) {
                    if (player.y < height - player.getImage().height) {
                        dy = player.getSpeed();
                    }   
                }
                
                player.move(dx, dy);
                
                // If the player is carrying the mountain, move the mountain by the same amount
                if (isCarryingMountain) {
                    mountain.move(dx, dy);
                }
            }
            
            //dialogue for stage 2
            
            if (touchingMountain) {
                image(emptyDialogue, 0, 265);
                fill(255);
                textAlign(LEFT, TOP);
                
                if (dialogueStage2Step < dialogueStage2Lines.size() -1){
                    textSize(26);
                    text(dialogueStage2Lines.get(dialogueStage2Step), 40, 285, 620, 100);
                } else {
                    //decision to pick up mountain or not
                    textSize(24);
                    text("Will you lift this burden to confront the Demon and save Sita?", 40, 280);
                    
                    fill(50,220,50);
                    textSize(20);
                    text("Press 'Y' to carry the mountain.", 40, 320);
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
            
            player.update(); //check if player has awakened
            
            if (bossDoneDialogue == false) {
                image(emptyDialogue, 0,265); //draw the dialogue box
                
                fill(255);
                textSize(26);
                textAlign(LEFT,TOP);
                
                if (dialogueStage3Step < dialogueStage3Lines.size()) {
                    // Setting a bounding box (x, y, width, height) lets the text auto-wrap inside the box
                    text(dialogueStage3Lines.get(dialogueStage3Step), 40, 285, 620, 100);
                }
            } else {
                //BOSS MOVEMENT
                // boss y position
                float bossY = demonBoss.y +(bossSpeed * bossDirectionY);
                //check screen bounds
                if (bossY < 0 || bossY > height - 125) { 
                    bossDirectionY *= -1; //reverse boss direction if reach ends of screen
                }
                //auto movement of boss
                demonBoss.move(0, (int)(bossSpeed * bossDirectionY));

                //BOSS ATTACKS (RANDOM)
                if (frameCount % 20 == 0) { //for each 60 frames, one projectile is thrown
                    //spawn projectile form boss current posiiton
                    Throw bossAttack = new Throw(this, demonBoss.x, demonBoss.y, "images/demonFireballs.png", -10);
                    bossAttacks.add(bossAttack);
                }
                
                //BOSS ATTACKS (PATTERN)
                if (frameCount % 80 == 0) { 
                    int spacingX = 40; // Horizontal distance between fireballs
                    int spacingY = 50; // Vertical distance between fireballs

                    // Loop through the 2D array to spawn the patterned grid
                    for (int r = 0; r < bossPattern.length; r++) {
                        for (int c = 0; c < bossPattern[r].length; c++) {
                            if (bossPattern[r][c] == 1) {
                                int spawnX = demonBoss.x + (c * spacingX);
                                int spawnY = demonBoss.y + (r * spacingY);

                                Throw bossAttack = new Throw(this, spawnX, spawnY, "images/demonFireballs.png", -10);
                                bossAttacks.add(bossAttack);
                            }
                        }
                    }
                }

                for (int i = bossAttacks.size() - 1; i>=0; i--) {
                    Throw ba = bossAttacks.get(i);
                    ba.update(); //speed at which boss projectiles goes left
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
                
                // movement
                if (keyPressed) {
                    int dx = 0;
                    int dy = 0;

                    if (keyCode == LEFT) {
                        if (player.x > 0) {
                            dx = -player.getSpeed();
                        }
                    } else if (keyCode == RIGHT) {
                        dx = player.getSpeed();
                    } else if (keyCode == UP) {
                        if (player.y > 0) {
                            dy = -player.getSpeed();
                        }
                    } else if (keyCode == DOWN) {
                        if (player.y < height - player.getImage().height) {
                            dy = player.getSpeed();
                        }   
                    }

                    player.move(dx, dy);

                    // If the player is carrying the mountain, move the mountain by the same amount
                    if (isCarryingMountain) {
                        mountain.move(dx, dy);
                    }
                }
            }
            
            player.draw();
            demonBoss.draw();
            mountain.draw();
            
            drawHealthBar();
            drawBossHealthBar();
            
            manageProjectiles();
            
        } else if (stage == 9) {
            fill(0);
            image(gameOver, 0,0, width, height); //loads new room bg
            
        } else if (stage == 4) {
            fill(0);
            image(dungeonbg, 0, 0, width, height);
            
            player.draw();
            sitaNPC.draw();
            mountain.draw();
            drawHealthBar();
            
            // movement
            if (keyPressed) {
                int dx = 0;
                int dy = 0;
                
                if (keyCode == LEFT) {
                    if (player.x > 0) {
                        dx = -player.getSpeed();
                    }
                } else if (keyCode == RIGHT) {
                    dx = player.getSpeed();
                } else if (keyCode == UP) {
                    if (player.y > 0) {
                        dy = -player.getSpeed();
                    }
                } else if (keyCode == DOWN) {
                    if (player.y < height - player.getImage().height) {
                        dy = player.getSpeed();
                    }   
                }
                
                player.move(dx, dy);
                
                // If the player is carrying the mountain, move the mountain by the same amount
                if (isCarryingMountain) {
                    mountain.move(dx, dy);
                }
            }
            
            if (player.isCollidingWith(sitaNPC)) {
                image(emptyDialogue, 0,265); //draw the dialogue box
                
                fill(255);
                textSize(26);
                textAlign(LEFT,TOP);
                
                if (!dialogueStage4Lines.isEmpty() && dialogueStage4Step < dialogueStage4Lines.size()) {
                    // Setting a bounding box (x, y, width, height) lets the text auto-wrap inside the box
                    text(dialogueStage4Lines.get(dialogueStage4Step), 40, 285, 620, 100);
                }
            }
            if (dialogueStage4Step >= dialogueStage4Lines.size()) {
                stage = 10;
            }
            
        } else if (stage == 10) {
            fill(0);
            image(endScreen, 0,0, width, height);
            textSize(32);
            fill(50,250,50);
            textAlign(CENTER, CENTER);
            text("SITA IS RESCUED!", width/2, 300);
            
            textSize(20);
            fill(255);
            text("Press ENTER to return to main menu", width/2, 360);
        }
        
    }
            
    public void keyPressed() {
        if (stage == 9 || stage == 10) {
            player.playerHealth = player.MAX_HEALTH;
            player.x = 200;
            player.y = 200;
            
            player.resetPowerUp();
            player.image = loadImage("images/monkeykingidle.png");
            

            
            //remove any remaining projectiles
            projectiles.clear();
            bossAttacks.clear();
            
            bossHealth = BOSS_MAX_HEALTH;
            
            isCarryingMountain = false;
            mountain.x = 342;
            mountain.y = 238;
            
            dialogueStep = 0;
            dialogueStage2Step = 0;
            dialogueStage3Step = 0;
            dialogueStage4Step = 0; // Reset stage 4 counter    
            
            stage = 0;
            return;
        }
        
        if (stage == 0) {
            if (keyCode == ENTER) {
                stage = 1;
            }
        }
        
        if (stage == 1) {
            if (key == ENTER && player.isCollidingWith(ramNPC)) {
                System.out.println(dialogueStep + " " + dialogueLines.size());
                if (dialogueStep < dialogueLines.size() - 1) { //stop the index from going out of number of lines said in stage 1
                    dialogueStep++;
                } else {
                    //if on the last line close dialogue
                    dialogueStep = 0;
                    //move player away from npc so aren't colliding -> box doesnt show
                    player.x += 40;
                }
            }
        }  else if (stage == 2) {
                if (key == ENTER && player.isCollidingWith((Object) mountain)&& !isCarryingMountain) {
                System.out.println(dialogueStage2Step + " " + dialogueStage2Lines.size() + " " + isCarryingMountain);
                    System.out.println(dialogueStage2Step);
                    if (dialogueStage2Step < dialogueStage2Lines.size() - 1) {
                        dialogueStage2Step++;
                    } else {
                        dialogueStage2Step = 0;
                    }
                } 
                if (key == 'y' || key == 'Y') { //carrying mech + checking if carry
                    //vertical align placing right under the mountain
                    player.y = mountain.getPosY() + mountain.image.height - 30;
                    //horizontal  align centering along the mountain width
                    player.x = mountain.getPosX() + (mountain.image.width / 2) - (player.getImage().width / 2);
                    isCarryingMountain = true;
                    dialogueStage2Step = 0;
                }
            } else if (stage == 3) {
                if (key == ENTER) {
                System.out.println(dialogueStage3Step + " " + dialogueStage3Lines.size());
                    System.out.println(dialogueStage3Step);
                    if (dialogueStage3Step < dialogueStage3Lines.size() - 1) {
                        dialogueStage3Step++;
                    } else {
                        bossDoneDialogue = true;
                        dialogueStage3Step = 0;
                    }
                }
                if (key == ' ') { //checks spacebar
                    
                    if (bossDoneDialogue) {
                        // Create new projectile at the player x and y coordinates
                        Throw newProjectile = new Throw(this, player.x, player.y, "images/mountainResize.png");

                        // Add it to our active projectiles list
                        projectiles.add(newProjectile);
                    } else {
                        System.out.println("You can't attack yet! The boss is speaking.");
                    }
                }
        } else if (stage == 4) {
            if (key == ENTER && player.isCollidingWith(sitaNPC)) {
                if (dialogueStage4Step < dialogueStage4Lines.size()) {
                    dialogueStage4Step++;
                }
            }
        } else if (stage == 10) {
            if (keyCode == ENTER) {
                player.playerHealth = player.MAX_HEALTH;
                player.x = 200;
                player.y = 200;

                //remove any remaining projectiles
                projectiles.clear();
                bossAttacks.clear();

                bossHealth = BOSS_MAX_HEALTH;

                isCarryingMountain = false;
                mountain.x = 342;
                mountain.y = 238;

                dialogueStep = 0;
                dialogueStage2Step = 0;
                dialogueStage3Step = 0;
                dialogueStage4Step = 0; // Reset stage 4 counter    

                stage = 0;
            }
        }
    }
    
    public void mousePressed() {
        System.out.println("x: " + mouseX + " y: "  + mouseY);
    }
    
    //method to keep stage blocks organized
    public void manageProjectiles() {
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Throw p = projectiles.get(i);
            p.update();
            p.draw();
            
            //Check collision with the Boss if we are on Stage 3
            if (stage == 3 && demonBoss.isCollidingWith(p)) { //if boss collided with projectile
                System.out.println("Boss hit");
                bossHealth -= mountain.getThrowDmg(); //per hit, reduce by 28 hp
                projectiles.remove(i);
                
                //check for if boss has died
                if (bossHealth <= 0) {
                    bossHealth = 0;
                    stage = 4;
                    bossAttacks.clear();
                    player.x = 100;
                    player.y = 200;
                    
                    //ensures that mountain is perfectly on top of player
                    if (isCarryingMountain) {
                        mountain.x = player.x + (player.getImage().width / 2) - (mountain.image.width / 2);
                        mountain.y = player.y - mountain.image.height + 30; 
                    }
                    return;
                }
            }
            // Check if it not on screen anymore, if yes then remove it
            if (!p.isInScreen()) {
                projectiles.remove(i);
            }
        }
    }
    
    
    //HEALTH OF MONKEY KING
    public void drawHealthBar() {
        
        //check for if player has died
        if (player.playerHealth <= 0) {
            player.playerHealth = 0;
            stage = 9;
            return;
        }

        float barWidth = 200; // Total width of the health bar
        float barHeight = 20; // Height of the health bar
        float x = 20;         // X position on the screen
        float y = 20;         // Y position on the screen

        //calculate the health ratio
        float healthRatio = (float) player.playerHealth / player.MAX_HEALTH;

        // Stop the bar from stretching if health is out of bounds
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
    
    public void drawBossHealthBar() {
        float barWidth = 200;
        float barHeight = 20;
        float x = width - barWidth - 20; //position it 20px from right of screen
        float y = 20;
        
        //calculate boss health ratio
        float healthRatio = (float) bossHealth/BOSS_MAX_HEALTH;
        healthRatio  = constrain(healthRatio, 0, 1);
        
        fill(80,80,80);
        noStroke();
        rect(x,y,barWidth,barHeight, 5);
        
        fill(255,69,0);
        rect(x,  y, barWidth * healthRatio, barHeight, 5);
        
        noFill();
        stroke(0);
        strokeWeight(2);
        rect(x, y, barWidth, barHeight, 5);
        
        //name of health bar
        fill(255);  
        textSize(14);
        textAlign(RIGHT, BOTTOM);
        text("DEMON BOSS", x + barWidth, y - 2);
    }
}
