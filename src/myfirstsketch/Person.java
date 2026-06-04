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
    private int x, y ; //postion of person
    private PlayerStats stats;
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
}
