/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstsketch;
import processing.core.PApplet;

/**
 *
 * @author 345954069
 */
public class MySketch extends PApplet{
    private Person player;
    int stage = 0;
    
    public void settings() {
        size(400,400);
    }
    
    public void setup() {
        background(255);
        textSize(20);
        player = new Person (this, 200, 200, "images/Monkey_King_Idle.png", new PlayerStats(100, 3), new Throw(199, 199, 9, false));
    }
    
    public void draw() {
        background(255, 0 ,0);
        
        if (stage == 0) {
            fill(0);
            text("My Cultural Story", 20,50);
            text("Press any key to continue", 20, 100); 
            
        } else if (stage == 1) {
            player.draw();
            
            if (keyPressed) {
                if (keyCode == LEFT) {
                    player.move(-5,0);
                } else if (keyCode == RIGHT) {
                    player.move(5,0);
                } else if (keyCode == UP) {
                    player.move(0, -5);
                } else if (keyCode == DOWN) {
                    player.move(0, 5);
                }
  
            }
        }
    }
            
    public void keyPressed() {
        if (stage == 0) {
            if (keyCode == ENTER) {
                stage = 1;
            }
        }
    }
    
    }
