package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.HelloApplication;
import com.example.nuclearthrone.model.Vector;

public class Bullet extends Entity {

    public int damage;
    double speed;

    public Bullet(double width, double height, int health, int damage) {
        super(Avatar.getIntance().getX(), Avatar.getIntance().getY(), width, height, health, true);
        this.damage = damage;
    }

    @Override
    public void takeDamage(int damage) {

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
                    Thread.sleep(HelloApplication.msRate());
                } catch (InterruptedException e) {

                }
            }
        });
        t.start();
    }
}
