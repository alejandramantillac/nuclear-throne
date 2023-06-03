package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.Vector;

public class Bullet extends Entity {

    double speed;

    public Bullet(Entity entity, double width, double height, int health, int damage) {
        super(entity.getX(), entity.getY(), width, height, health, true);
        this.damage = damage;
    }

    @Override
    public void takeDamage(Entity other) {

    }

    public void shootTo(double x, double y) {
        Vector movement = new Vector(x-getX(), y-getY());
        movement.normalize();
        movement.setMag(speed);
        Thread t = new Thread(() -> {
            while (0 != 1) {
                setX(getX() + movement.x);
                setY(getY() + movement.y);
                try {
                    Thread.sleep(App.msRate());
                } catch (InterruptedException e) {

                }
            }
        });
        t.start();
    }
}
