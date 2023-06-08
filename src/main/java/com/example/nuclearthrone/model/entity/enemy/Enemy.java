package com.example.nuclearthrone.model.entity.enemy;

import java.util.Objects;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.*;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.menus.Soundtrack;
import com.example.nuclearthrone.model.util.Vector;

public abstract class Enemy extends MovableEntity {

    public static final double DISTANCE_IDLE = 70;

    double targetX;
    double targetY;

    public Enemy(double x, double y, double width, double height, int health, int damage, int speed, int level) {
        super(x, y, width, height, health, damage,speed,level);
        targetX = -1;
        targetY = -1;
    }

    public abstract void attack(Entity entity);

    @Override
    public void takeDamage(Entity other) {
        health -= other.damage;
        if (health <= 0) {
            Soundtrack.getInstance().reproduceSound("enemyKilled_sound",false);
            isAlive = false;
            Objects.requireNonNull(Level.getLevel(level)).entities.remove(this);
        }
    }

    @Override
    public void movement() {
        if (isEntityVisible(Avatar.getInstance())) {
            resetMovement();
            attack(Avatar.getInstance());
        } else {
            initMovement();
            moveToDirection(targetX, targetY);
        }
    }


    public void initMovement() {
        if (targetX == -1 || distanceTo(targetX, targetY) < 10) {
            targetX = getX()
                    + Vector.randNegOrPos() * (Math.random() * DISTANCE_IDLE + DISTANCE_IDLE / 3);
            targetY = getY()
                    + Vector.randNegOrPos() * (Math.random() * DISTANCE_IDLE + DISTANCE_IDLE / 3);
        }
    }

    @Override
    public void resetMovement() {
        targetX = -1;
    }

    public static Enemy generateEnemy(int level) {
        int type = (int) (Math.random() * 2);
        switch (type) {
            case 0 -> {
                return new Ghost(Math.random() * (MainMenu.getWidth() - Ghost.WIDTH),
                        Math.random() * (MainMenu.getHeight() - Ghost.HEIGHT), level);
            }
            case 1 -> {
                return new Knight(Math.random() * (MainMenu.getWidth() - Ghost.WIDTH),
                        Math.random() * (MainMenu.getHeight() - Ghost.HEIGHT), level);
            }
            default -> {
            }
        }
        return null;
    }
}