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
    private int playerHealth;
    private static final int MAX_HEALTH = 100;
    private int playerSpeed;
    private static final int BASE_PLAYER_SPEED = 7;
    private int speedMod = 1;
        
    public PlayerSprite(PApplet app, int x, int y, int monkeyState, String imagePath, Throw projectile) {
        super(app, x, y, imagePath, projectile);
        this.monkeyState = monkeyState;
        this.playerHealth = MAX_HEALTH;
        this.playerSpeed = BASE_PLAYER_SPEED;
    }
    
    public int getSpeed() {
        return playerSpeed * speedMod;
    }
    
    public int getMonkeyState() {
        return monkeyState;
    }
    
    public void update() {
        if (playerHealth <= MAX_HEALTH/2) {
            monkeyState++;
            speedMod = 2;
        }
    }
}
