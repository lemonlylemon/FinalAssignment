/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstsketch;

/**
 *
 * @author 345954069
 */
public class Throw {
    private int x, y;
    private int objectSpeed;
    private static final int BASE_PROJECTILE_SPEED = 10;
    private int damage;
    private static final int BASE_DAMAGE = 5;
    private int damageMod;
    
    // Define screen dimensions (adjust these numbers to match your actual window size)
    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 400;
    
    public Throw(int x, int y) {
        this.x = x;
        this.y = y;
        this.objectSpeed = BASE_PROJECTILE_SPEED;
        this.damage = BASE_DAMAGE;
    }
    
    public int getPosX() {
        return x;
    }
    
    public int getPosY() {
        return y;
    }
    
    public int getObjSpeed() {
        return objectSpeed;
    }
    
    public void setObjSpeed(int speed) {
        this.objectSpeed = speed;
    }
    
    public int getThrowDmg() {
        return damage;
    }
    
//    if ( .getMonkeyState() == 0)
    
    /**
     * Helper method to check if the projectile is currently on the screen.
     * @return returns true of false based on whats said above
     */
    public boolean isInScreen() {
        return x >= 0 && x <= SCREEN_WIDTH && y >= 0 && y <= SCREEN_HEIGHT;    
    }
    
    public void spawn(int shooterX, int shooterY) {
        if (isInScreen()) {
            return;
        }
        // Otherwise, reset its position to the shooter's position
        this.x = shooterX;
        this.y = shooterY;
       }
}
