/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 *
 * @author Dee
 */
public class Ship extends BaseVectorShape {
    private int[] shipx = {-6,-3,0,6,0};
    private int[] shipy ={6,7,7,7,6,-7};
    
    public Rectangle getBounds(){
        return new Rectangle( (int) getX()-6, (int) getY()-6, 12,12);
    }
    public Ship(){
        setShape(new Polygon(shipx,shipy,shipx.length));
        setAlive(true);
    }
}
