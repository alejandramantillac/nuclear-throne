package com.example.nuclearthrone.model;

import javafx.scene.image.Image;

public abstract class Entity {

    int x;
    int y;
    double width;
    double height;
    Image sprite;
    int health;

    public Entity(int x, int y, double width, double height, int health) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        // this.sprite = new Image("PATH_TO_DEFAULT", width, height, false, false,
        // false);
    }

    public abstract void takeDamage(int damage);
}
