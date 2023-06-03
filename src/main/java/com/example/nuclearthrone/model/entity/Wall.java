package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.model.level.Level;

public class Wall extends Entity {

    int level;
    public Wall(double x, double y, double width, double height, int health, int level) {
        super(x, y, width, height, health, true);
        this.level = level;
    }

    @Override
    public void takeDamage(Entity other) {
        if(other instanceof EnemyBullet)
            return;

        health -= (other.damage / 100) + 1;
        if (health <= 0) {
            Level.getLevel(level).walls.remove(this);
        }
    }

}
