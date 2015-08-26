/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Shape;

/**
 *
 * @author Dee
 */
public class BaseVectorShape {

    private Shape shape;
    private boolean alive;
    private double x, y;
    private double velx, vely;
    private double moveAngle, faceAngle;

    public BaseVectorShape() {
        setShape(null);
        setAlive(false);
        setX(0.0);
        setY(0.0);
        setVelx(0.0);
        setVely(0.0);
        setMoveAngle(0.0);
        setFaceAngle(0.0);
    }

    public double getMoveAngle() {
        return moveAngle;
    }
    public void incMoveAngle(double moveAngle){
        this.moveAngle += moveAngle;
    }
    public void setMoveAngle(double moveAngle) {
        this.moveAngle = moveAngle;
    }
    public void incFaceAngle(double faceAngle){
        this.faceAngle += faceAngle;
    }
    public void setFaceAngle(double faceAngle) {
        this.faceAngle = faceAngle;
    }

    public double getFaceAngle() {
        return faceAngle;
    }

    public Shape getShape() {
        return shape;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void incX(double x) {
        this.x += x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void incY(double y) {
        this.y += y;
    }

    public void setVelx(double velx) {
        this.velx = velx;
    }

    public void incVelX(double velx) {
        this.velx += velx;
    }

    public void setVely(double vely) {
        this.vely = vely;
    }

    public void incVelY(double velY) {
        this.vely += velY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelx() {
        return velx;
    }

    public double getVely() {
        return vely;
    }
}
