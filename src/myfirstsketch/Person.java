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
    public PlayerStats stats;
    private Throw projectile;
    private PImage image;
    private PApplet app;
    
        
    public Person(PApplet p, int x, int y, String imagePath, PlayerStats stats, Throw projectile) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = app.loadImage(imagePath);
        this.stats = stats;
        this.projectile = projectile;
    }
    
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    public void draw() {
        app.image(image, x, y); //draw image at person position

    }
    
    public PlayerStats getStats() {
        return stats;
    }
    
    public boolean isCollidingWith(Person other) {
        // Check if the bounding boxes of the two persons intersect
        boolean isLeftOfOtherRight = x < other.x + other.image.width;
        boolean isRightOfOtherLeft = x + image.width > other.x;
        boolean isAboveOtherBottom = y < other.y + other.image.height;
        boolean isBelowOtherTop = y + image.height > other.y;

        return isLeftOfOtherRight && isRightOfOtherLeft
                && isAboveOtherBottom && isBelowOtherTop;

    }
}
