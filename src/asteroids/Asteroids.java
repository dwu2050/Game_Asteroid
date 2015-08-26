/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

/**
 *
 * @author Dee
 */
public class Asteroids extends Applet implements Runnable, KeyListener {

    Thread gameloop;
    BufferedImage backbuffer;
    Graphics2D g2d;
    boolean showBounds = false;
    int ASTEROIDS = 20;
    Asteroid[] ast = new Asteroid[ASTEROIDS];
    int BULLETS = 10;
    Bullet[] bullet = new Bullet[BULLETS];
    Ship ship = new Ship();
    AffineTransform identity = new AffineTransform();
    Random ran = new Random();
    int currentBullet = 0;

    public void init() {
        backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();

        ship.setX(320);
        ship.setY(240);

        for (int i = 0; i < BULLETS; i++) {
            bullet[i] = new Bullet();
        }

        for (int i = 0; i < ASTEROIDS; i++) {
            ast[i] = new Asteroid();
            ast[i].setRotationVelocity(ran.nextInt(3) + 1);
            ast[i].setX((double) ran.nextInt(600) + 20);
            ast[i].setY((double) ran.nextInt(440) + 20);
            ast[i].setMoveAngle(ran.nextInt(360));
            double ang = ast[i].getMoveAngle() - 90;
            ast[i].setVelx(calcAngleMoveX(ang));
            ast[i].setVely(calcAngleMoveY(ang));

        }
        addKeyListener(this);
    }

    public void update(Graphics g) {
        g2d.setTransform(identity);

        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getSize().width, getSize().height);

        g2d.setColor(Color.WHITE);
        g2d.drawString("Ship: x, y " + Math.round(ship.getX()) + "," + Math.round(ship.getY()), 5, 10);
        g2d.drawString("Move angle: " + Math.round(ship.getMoveAngle()) + 90, 5, 25);
        g2d.drawString("Face angle: " + Math.round(ship.getMoveAngle()) + "," + Math.round(ship.getY()), 5, 40);

        drawShip();
        drawBullets();
        drawAsteroids();

        paint(g);
    }

    public void drawShip() {
        g2d.setTransform(identity);
        g2d.translate(ship.getX(), ship.getY());
        g2d.rotate(Math.toRadians(ship.getFaceAngle()));
        g2d.setColor(Color.ORANGE);
        g2d.fill(ship.getShape());
    }

    public void drawBullets() {
        for (int i = 0; i < BULLETS; i++) {
            if (bullet[i].isAlive()) {
                g2d.setTransform(identity);
                g2d.translate(bullet[i].getX(), bullet[i].getY());
                g2d.draw(bullet[i].getShape());
            }
        }
    }

    public void drawAsteroids() {
        for (int i = 0; i < ASTEROIDS; i++) {
            if (ast[i].isAlive()) {
                g2d.setTransform(identity);
                g2d.translate(ast[i].getX(), ast[i].getY());
                g2d.rotate(Math.toRadians(ast[i].getMoveAngle()));
                g2d.setColor(Color.DARK_GRAY);
                g2d.fill(ast[i].getShape());
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(backbuffer, 0, 0, this);
    }

    public void start() {
        gameloop = new Thread(this);
        gameloop.start();
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();

        while (t == gameloop) {
            try {
                gameUpdate();
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    public void stop() {
        gameloop = null;
    }

    public void gameUpdate() {
        updateShip();
        updateBullets();
        updateAsteroids();
        checkCollisions();
    }

    public void updateShip() {
        ship.incX(ship.getVelx());

        if (ship.getX() < -10) {
            ship.setX(getSize().width + 10);
        } else if (ship.getX() > getSize().width + 10) {
            ship.setX(-10);
        }

        ship.incY(ship.getVelx());

        if (ship.getY() < -10) {
            ship.setY(getSize().height + 10);
        } else if (ship.getX() > getSize().height + 10) {
            ship.setY(-10);
        }
    }

    public void updateBullets() {
        for (int i = 0; i < BULLETS; i++) {
            if (bullet[i].isAlive()) {
                bullet[i].incX(bullet[i].getVelx());

                if (bullet[i].getX() < 0 || bullet[i].getX() > getSize().width) {
                    bullet[i].setAlive(false);
                }

                bullet[i].incY(bullet[i].getVely());

                if (bullet[i].getY() < 0 || bullet[i].getY() > getSize().width) {
                    bullet[i].setAlive(false);
                }
            }
        }
    }

    public void updateAsteroids() {
        for (int i = 0; i < ASTEROIDS; i++) {
            if (ast[i].isAlive()) {
                ast[i].incX(ast[i].getVelx());
            }

            if (ast[i].getX() < -20) {
                ast[i].setX(getSize().width + 20);
            } else if (ast[i].getX() > getSize().width + 20) {
                ast[i].setX(-20);
            }

            ast[i].incY(ast[i].getVely());

            if (ast[i].getY() < -20) {
            } else if (ast[i].getY() > getSize().width + 20) {
                ast[i].setY(-20);
            }

            ast[i].incMoveAngle(ast[i].getRotationVelocity());

            if (ast[i].getMoveAngle() < 0) {
                ast[i].setMoveAngle(360 - ast[i].getRotationVelocity());
            } else if (ast[i].getMoveAngle() > 360) {
                ast[i].setMoveAngle(ast[i].getRotationVelocity());
            }

        }
    }

    public void checkCollisions() {
        for (int i = 0; i < ASTEROIDS; i++) {
            if (ast[i].isAlive()) {
                for (int j = 0; j < BULLETS; j++) {

                    if (bullet[j].isAlive()) {
                        if (ast[i].getBounds().contains(bullet[j].getX(), bullet[j].getY())) {
                            bullet[j].setAlive(false);
                            ast[i].setAlive(false);
                            continue;
                        }
                    }
                }
            }

            if (ast[i].getBounds().intersects(ship.getBounds())) {
                ast[i].setAlive(false);
                ship.setY(320);
                ship.setY(240);
                ship.setFaceAngle(0);
                ship.setVelx(0);
                ship.setVely(0);
                continue;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
       
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                ship.incFaceAngle(-5);
                if (ship.getFaceAngle() < 0) {
                    ship.setFaceAngle(360 - 5);
                }
                break;
            case KeyEvent.VK_RIGHT:
                ship.incFaceAngle(5);
                if (ship.getFaceAngle() > 360) {
                    ship.setFaceAngle(5);
                }
                break;

            case KeyEvent.VK_UP:
                ship.setMoveAngle(ship.getFaceAngle() - 90);
                ship.incVelX(calcAngleMoveX(ship.getMoveAngle() * 0.01));
                ship.incVelY(calcAngleMoveY(ship.getMoveAngle() * 0.01));
                break;

            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                currentBullet++;

                if (currentBullet > BULLETS - 1) {
                    currentBullet = 0;
                }

                bullet[currentBullet].setAlive(true);
                
                
                bullet[currentBullet].setX(ship.getX());
                bullet[currentBullet].setY(ship.getY());
                bullet[currentBullet].setMoveAngle(ship.getFaceAngle() - 90);

                double angle = bullet[currentBullet].getFaceAngle();
                double svx = ship.getVelx();
                double svy = ship.getVely();
                bullet[currentBullet].setVelx(svx + calcAngleMoveX(angle * 2));
                bullet[currentBullet].setVely(svy + calcAngleMoveY(angle * 2));
                break;

        }
    }

    public double calcAngleMoveX(double angle) {
        return (double) (Math.cos(angle * Math.PI / 180));
    }

    public double calcAngleMoveY(double angle) {
        return (double) (Math.sin(angle * Math.PI / 180));
    }

    @Override
    public void keyReleased(KeyEvent ke) {
       
    }
}
