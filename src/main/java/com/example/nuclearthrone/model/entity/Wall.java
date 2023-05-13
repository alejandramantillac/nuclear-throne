package com.example.nuclearthrone.model.entity;

public class Wall extends Entity {

    public Wall(int x, int y, double width, double height, int health) {
        super(x, y, width, height, health, true);
    }

    @Override
    public void takeDamage(int damage) {
    }

}
