/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstsketch;

/**
 *
 * @author 345954069
 */
public class PlayerStats {
    private int playerHealth;
    private int playerSpeed = 10;
    
    public PlayerStats(int health, int speed) {
        this.playerHealth = health;
        this.playerSpeed = speed;
    }
    
    public int getHealth() {
        return playerHealth;
    }
    
    public void setHealth(int health) {
        this.playerHealth = health;
    }
    
    public int getSpeed() {
        return playerSpeed;
    }
    
    public void setSpeed(int speed) {
        this.playerSpeed  = speed;
    }
}
