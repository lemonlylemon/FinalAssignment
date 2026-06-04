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
    private int playerSpeed;
    
    public PlayerStats(int health, int speed) {
        this.playerHealth = health;
        this.playerSpeed = speed;
    }
    
    public int getHealth() {
        return playerHealth;
    }
    
    public int getSpeed() {
        return playerSpeed;
    }
}
