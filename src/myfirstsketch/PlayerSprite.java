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
    public int playerHealth;
    public static final int MAX_HEALTH = 100;
    private int playerSpeed;
    private static final int BASE_PLAYER_SPEED = 7;
    private int playerSpeedMod = 1;
    private int damageMod = 1;
        
    public PlayerSprite(PApplet app, int x, int y, int monkeyState, String imagePath) {
        super(app, x, y, imagePath);
        this.monkeyState = monkeyState;
        this.playerHealth = MAX_HEALTH;
        this.playerSpeed = BASE_PLAYER_SPEED;
    }
    
    public int getSpeed() {
        return playerSpeed * playerSpeedMod;
    }
    
    public int getMonkeyState() {
        return monkeyState;
    }
    
    @Override
    public void update() {
        super.update();
        
        if (playerHealth <= MAX_HEALTH/2 && monkeyState == 0) {
            monkeyState = 1;
            playerSpeedMod = 2;
            damageMod = 2;
            
            PImage powerUpImage = app.loadImage("images/supermonkeyking.png");
            this.setImage(powerUpImage);
            
            System.out.println("The Monkey King has awakened! Speed and Damage doubled!");
        }
    }
    
    // Add this inside your PlayerSprite class
    public void resetPowerUp() {
        this.monkeyState = 0;
        this.playerSpeedMod = 1;
        this.damageMod = 1;
    }
}
