/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstsketch;
import processing.core.PApplet;
import processing.core.PImage;
/**
 * Represents a projectile or thrown object asset within the game space.
 * Handles automatic horizontal movement updates, custom speed settings, damage values,
 * and tracks boundaries to determine whether the asset is actively on screen.
 * * @author 345954069
 */
public class Throw {
    public int x, y; //horizontol and vertical projectile postiions
    public PImage image;
    private PApplet app;
    private int objectSpeed;
    private static final int BASE_PROJECTILE_SPEED = 10;
    private int damage;
    private static final int BASE_DAMAGE = 28;
    private int damageMod;
    
    // Define screen dimensions
    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 400;
    
    /**
     * Constructs a baseline Throw projectile instance centered on default speed and damage rules.
     * * @param p          The active PApplet sketch surface instance.
     * @param x          The starting horizontal position on the screen grid.
     * @param y          The starting vertical position on the screen grid.
     * @param imagePath  The folder pathway pointing to the projectile's sprite asset file.
     */
    public Throw(PApplet p, int x, int y, String imagePath) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = app.loadImage(imagePath);
        this.objectSpeed = BASE_PROJECTILE_SPEED;
        this.damage = BASE_DAMAGE;
    }
    
    /**
     * Constructs an overloaded Throw projectile instance featuring customizable velocity traits.
     * * @param p           The active PApplet sketch surface instance.
     * @param x           The starting horizontal position on the screen grid.
     * @param y           The starting vertical position on the screen grid.
     * @param imagePath   The folder pathway pointing to the projectile's sprite asset file.
     * @param customSpeed The manual horizontal movement velocity value assigned to this project.
     */
    public Throw(PApplet p, int x, int y, String imagePath, int customSpeed) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = app.loadImage(imagePath);
        this.objectSpeed = customSpeed; /// Override default rules with unique speed parameters
        this.damage = BASE_DAMAGE;
    }
    
    /**
     * Adjusts the projectile's positioning manually via custom delta shift offsets.
     * Primarily used if an object needs to be shifted or anchored to a moving entity.
     * * @param dx The amount of pixels to shift horizontally.
     * @param dy The amount of pixels to shift vertically.
     */
    // Updates position coordinates when carrying or moving
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    
    /**
     * Retrieves the current horizontal X-axis position coordinates of the projectile.
     * * @return The horizontal position coordinate integer.
     */
    public int getPosX() {
        return x;
    }
    
    /**
     * Retrieves the current vertical Y-axis position coordinates of the projectile.
     * * @return The vertical position coordinate integer.
     */
    public int getPosY() {
        return y;
    }
    
    /**
     * Retrieves the underlying movement speed attribute of this projectile.
     * * @return The speed value integer.
     */
    public int getObjSpeed() {
        return objectSpeed;
    }
    
    /**
     * Updates or alters the horizontal movement velocity attribute of this projectile.
     * * @param speed The new target movement speed value integer.
     */
    public void setObjSpeed(int speed) {
        this.objectSpeed = speed;
    }
    
    /**
     * Retrieves the final raw offensive base damage calculated for this projectile asset.
     * * @return The raw damage impact integer.
     */
    public int getThrowDmg() {
        return damage;
    }
    
    /**
     * Drives projectile tracking state updates once per engine clock loop iteration.
     * Updates positions automatically to move the projectile from left to right across the window.
     */
    public void update() {
    // Moves the projectile automatically to the right based on its speed
    this.x += this.objectSpeed;
}
       
    /** 
     * Helper method to check if the projectile is currently on the screen.
     * Evaluates boundary positions against configured window constraints.
     * * @return true if the projectile's coordinates fall within screen boundaries; false otherwise.
     */
    public boolean isInScreen() {
        return x >= 0 && x <= SCREEN_WIDTH && y >= 0 && y <= SCREEN_HEIGHT;    
    }
    
    /**
     * Sets up or spawns the projectile asset directly at a shooter's coordinates.
     * Safeguards logic to verify that active, moving on-screen projectiles are not accidentally interrupted.
     * * @param shooterX The current horizontal X-coordinate of the entity firing this projectile.
     * @param shooterY The current vertical Y-coordinate of the entity firing this projectile.
     */
    public void spawn(int shooterX, int shooterY) {
        //check if it is actively moving across the screen, skip instantiation overrides
        if (isInScreen()) {
            return;
        }
        // Otherwise, reset its position to the shooter's position
        this.x = shooterX;
        this.y = shooterY;
       }
    
    /**
     * Renders the projectile's sprite asset visual texture onto the processing display window,
     * provided that the image framework has been loaded completely.
     */
    public void draw() {
        if (this.image != null) {
            this.app.image(this.image, this.x, this.y);
        }
    }
}
