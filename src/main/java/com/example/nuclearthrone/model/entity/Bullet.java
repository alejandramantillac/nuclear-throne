package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.Vector;
import com.example.nuclearthrone.model.level.Level;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bullet extends Entity {

    public double speed;
    boolean alive;
    Vector movement;
    int level;

    public Bullet(Entity entity, double width, double height, int health, int damage, int level) {
        super(entity.getX()+(entity.getWidth()/2), entity.getY()+(entity.getHeight()/2), width, height, health, true);
        this.damage = damage;
        alive = true;
        this.level = level;

    }

    @Override
    public void takeDamage(Entity other) {

    }

    public void shootTo(double x, double y, double delay) {
        movement = new Vector(x-getX(), y-getY());
        movement.normalize();
        movement.setMag(speed);
        sprite = rotateImage(sprite, Math.toDegrees(movement.angle));
        Thread t = new Thread(() -> {
            try {
                Thread.sleep((long) delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setVisible(true);
            while (alive) {
                setX(getX() + movement.x);
                setY(getY() + movement.y);
                alive = !isOutOfScreen(this);
                try {
                    Thread.sleep(App.msRate());
                } catch (InterruptedException e) {
                }
            }
            Level.getLevel(level).bullets.remove(this);
        });
        t.start();
    }

    public static boolean isOutOfScreen(Entity e) {
        return e.getX() + e.getWidth() - 20> App.getWidth() || e.getX() < -20
                || e.getY()+e.getHeight() > App.getHeight() || e.getY()  < -20;
    }

}
