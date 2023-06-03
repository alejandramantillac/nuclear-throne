package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.model.level.Level;

public class Wall extends Entity {

    public Wall(double x, double y, double width, double height, int health) {
        super(x, y, width, height, health, true);
    }

    @Override
    public void takeDamage(int damage) {
        health -= (damage / 100) + 1;
        if (health <= 0) {

            Level.currentLevel().walls.remove(this);
        }
    }

}
