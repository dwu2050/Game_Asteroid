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
public class Asteroid extends BaseVectorShape{
    private int [] astx = {-20, -13,0,20,22,20,12,2,-10,-22,-16};
    private int [] asty = {20, 23,17,20,16,-20,-22,-14,-17,-20,-5};
    
    protected double rotVel;

    public Asteroid(){
        setShape(new Polygon(astx,asty,astx.length));
        setAlive(true);
        setRotationVelocity(0.0);
    }
    
    public void setRotationVelocity(double rotVel) {
        this.rotVel = rotVel;
    }

    public double getRotationVelocity() {
        return rotVel;
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)getX() - 20, (int) getY() - 20, 40,40);
    }
    
}
