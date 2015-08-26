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
public class Bullet extends BaseVectorShape {

    public Bullet() {
        setShape(new Rectangle(0, 0, 1, 1));
        setAlive(false);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), 1, 1);
    }
}
