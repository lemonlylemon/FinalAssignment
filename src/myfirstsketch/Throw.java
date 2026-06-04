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
    private boolean pierce = false;
    
    public Throw(int x, int y, int objectSpeed, boolean pierce) {
        this.x =x;
        this.y = y;
        this.objectSpeed = objectSpeed;
        this.pierce = pierce;
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
    
    public boolean getPierce() {
        return pierce;
    }
}
