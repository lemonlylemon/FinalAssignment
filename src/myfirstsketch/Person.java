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
public class Person {
    public int x, y ; //postion of person
    public PImage image;
    public PApplet app;
    
        
    public Person(PApplet p, int x, int y, String imagePath) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = app.loadImage(imagePath);
    }
    
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    public void draw() {
        app.image(image, x, y); //draw image at person position

    }
    
    public PImage getImage() {
    return this.image;
    }
    
    public void setImage(PImage newImage) {
    this.image = newImage;
    }
    
    public void update() {
    // Shared functionality: Keep ALL characters within horizontal screen bounds
    // (Assuming a standard 700-pixel wide screen)
    if (x < 0) {
        x = 0;
    } else if (x > 700 - (image != null ? image.width : 0)) {
        x = 700 - (image != null ? image.width : 0);
    }
    }
    
    // POLYMORPHIC METHOD TO RUN BOTH PERSON COLLISION AND THROW COLLISION
    public boolean isCollidingWith(Object other) {
    int otherX = 0;
    int otherY = 0;
    int otherWidth = 0;
    int otherHeight = 0;

    // Check if the passed object is a Person
    if (other instanceof Person) {
        Person p = (Person) other;
        otherX = p.x;
        otherY = p.y;
        otherWidth = p.image.width;
        otherHeight = p.image.height;
        
    // Check if the passed object is a Throw
    } else if (other instanceof Throw) {
        Throw t = (Throw) other;
        otherX = t.getPosX();
        otherY = t.getPosY();
        otherWidth = t.image.width; // Requires t.image to be public
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
