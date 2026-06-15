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
public class PlayerSprite extends Person {
    public int monkeyState = 0; //the state/power level of monkey (player)
    public int playerHealth; //current health
    public static final int MAX_HEALTH = 100;
    private int playerSpeed; //current speed
    private static final int BASE_PLAYER_SPEED = 7;
    private int playerSpeedMod = 1; //modded speed
    private int damageMod = 1; //modded dmg
    
    /**
     * Constructs a new PlayerSprite with specified positioning, initial state, and avatar graphics.
     * Automatically initializes player health to maximum and sets the default movement speed.
     * * @param app        The main PApplet sketch instance for loading and drawing assets.
     * @param x          The starting horizontal X-coordinate on the screen grid.
     * @param y          The starting vertical Y-coordinate on the screen grid.
     * @param monkeyState The initial progression state or form of the player.
     * @param imagePath  The file directory pathway pointing to the player's initial sprite image.
     */
    public PlayerSprite(PApplet app, int x, int y, int monkeyState, String imagePath) {
        super(app, x, y, imagePath); //call from parent
        this.monkeyState = monkeyState;
        this.playerHealth = MAX_HEALTH;
        this.playerSpeed = BASE_PLAYER_SPEED;
    }
    
    /**
     * Calculates and returns the real-time movement velocity of the player.
     * Automatically scales the base speed calculation against active transformation modifiers.
     * * @return The actual calculated movement speed integer.
     */
    public int getSpeed() {
        return playerSpeed * playerSpeedMod;
    }
    
    /**
     * Retrieves the player's active form state or transformation level.
     * * @return An integer representing the current state index.
     */
    public int getMonkeyState() {
        return monkeyState;
    }
    
    /**
     * Refreshes the player state once per game engine tick.
     * Runs inherited screen-boundary checks via the parent class, then evaluates health 
     * thresholds to trigger the dynamic "Awakened Monkey King" transformation.
     */
    @Override
    public void update() {
        super.update();
        
        // Check if player health drops to or below 50% while still in base form (state 0)
        if (playerHealth <= MAX_HEALTH/2 && monkeyState == 0) {
            monkeyState = 1; //increase state of monkey
            playerSpeedMod = 2; //double the current speed
            damageMod = 2; //double the current dmg
            
            //swap the visual texture to the super variant
            PImage powerUpImage = app.loadImage("images/supermonkeyking.png");
            this.setImage(powerUpImage);
            
            System.out.println("The Monkey King has awakened! Speed and Damage doubled!");
        }
    }
    
    /**
     * Resets transformation traits back to baseline defaults.
     * Strips active power-up multipliers and reverts the state index to base form.
     */
    public void resetPowerUp() {
        this.monkeyState = 0;
        this.playerSpeedMod = 1;
        this.damageMod = 1;
    }
}
