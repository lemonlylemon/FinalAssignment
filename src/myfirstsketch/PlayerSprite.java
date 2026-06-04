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
    private int throwSpeed; //speed of player's throw
    private int throwDamage; //damage of projectile thrown
    private boolean throwPierce = false; //ability of projectile to go through enemies
    private int monkeyState = 0; //the state/power level of monkey (player)
    
        
    public PlayerSprite(PApplet app, int x, int y, int throwSpeed, int throwDmg, boolean throwPierce, int monkeyState, String imagePath, PlayerStats stats, Throw projectile) {
        super(app, x, y, imagePath, stats, projectile);
        this.throwSpeed = throwSpeed;
        this.throwDamage = throwDmg;
        this.throwPierce = throwPierce;
        this.monkeyState = monkeyState;
    }
    
    public int getThrowSpeed() {
        return throwSpeed;
    }
    
    public int getThrowDmg() {
        return throwDamage;
    }
    
    public boolean getthrowPierce() {
        return throwPierce;
    }
    
    public int getMonkeyState() {
        return monkeyState;
    }
}
