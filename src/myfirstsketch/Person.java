/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstsketch;
import processing.core.PApplet;
import processing.core.PImage;
/**
 * Represents a basic entity or character within the game space.
 * Serves as the base superclass for player sprites and NPCs, providing essential 
 * properties and mechanics for positioning, screen-boundary updates, rendering, 
 * and AABB (Axis-Aligned Bounding Box) polymorphic collision handling.
 * * @author 345954069
 */
public class Person {
    public int x, y ; //postion of person
    public PImage image; //image used
    public PApplet app;
    
    /**
     * Constructs a new Person entity with an initial position and registers its visual texture asset.
     * * @param p          The active PApplet sketch surface instance.
     * @param x          The initial horizontal X-axis screen position.
     * @param y          The initial vertical Y-axis screen position.
     * @param imagePath  The localized folder pathway pointing to the image file to be loaded.
     */
    public Person(PApplet p, int x, int y, String imagePath) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = app.loadImage(imagePath);
    }
    
    /**
     * Updates the entity's position by adding horizontal and vertical delta offsets.
     * * @param dx The amount of pixels to shift left (negative) or right (positive).
     * @param dy The amount of pixels to shift up (negative) or down (positive).
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    /**
     * Draws the person's visual sprite texture onto the Processing display window 
     * at their designated (X, Y) coordinate position.
     */
    public void draw() {
        app.image(image, x, y); //draw image at person position

    }
    
    /**
     * Retrieves the current sprite asset image structure assigned to this person.
     * * @return The active PImage instance object.
     */
    public PImage getImage() {
    return this.image;
    }
    
    /**
     * Updates or swaps out the person's current visual texture asset.
     * * @param newImage The new PImage asset structure to bind to this entity.
     */
    public void setImage(PImage newImage) {
    this.image = newImage;
    }
    
    /**
     * Refreshes internal state logic once per system clock frame.
     * Contains shared framework utility behavior that actively prevents any game entity 
     * from walking past the left or right boundaries of the active 700-pixel wide window.
     */
    public void update() {
    // keep characters within horizontal screen bounds
    // (Assuming a standard 700-pixel wide screen)
    if (x < 0) {
        x = 0; // Prevent character from moving past the left edge
    } else if (x > 700 - (image != null ? image.width : 0)) {
       //check for image dimensions to prevent clipping off-screen
        x = 700 - (image != null ? image.width : 0);
    }
    }
    
    /**
     * A polymorphic collision method that determines if this Person is overlapping with another object.
     * Uses Java runtime reflection (`instanceof`) to safely downcast target parameters and extract 
     * bounding box limits from either structural {@link Person} objects or {@link Throw} projectiles.
     * * @param other The targeted target object element to test against for interception.
     * @return true if an active geometric collision overlap is detected; false otherwise.
     */
    // POLYMORPHIC METHOD TO RUN BOTH PERSON COLLISION AND THROW COLLISION
    public boolean isCollidingWith(Object other) {
    int otherX = 0;
    int otherY = 0;
    int otherWidth = 0;
    int otherHeight = 0;

    // Check if the passed object is a Person
    if (other instanceof Person) {
        Person p = (Person) other; // Downcast Object safely to access structural dimensions
        otherX = p.x;
        otherY = p.y;
        otherWidth = p.image.width;
        otherHeight = p.image.height;
        
    // Check if the passed object is a Throw
    } else if (other instanceof Throw) {
        Throw t = (Throw) other; // Downcast Object safely to access projectile coordinate methods
        otherX = t.getPosX();
        otherY = t.getPosY();
        otherWidth = t.image.width;
        otherHeight = t.image.height;
    } else {
        return false; // It's neither, so no collision is possible
    }

    // Run the bounding box math using the extracted values
    boolean isLeftOfOtherRight = x < otherX + otherWidth;
    boolean isRightOfOtherLeft = x + image.width > otherX;
    boolean isAboveOtherBottom = y < otherY + otherHeight;
    boolean isBelowOtherTop = y + image.height > otherY;

    return isLeftOfOtherRight && isRightOfOtherLeft
            && isAboveOtherBottom && isBelowOtherTop;
}
}
