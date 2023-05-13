package com.example.nuclearthrone.model.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity extends Rectangle {

    Image sprite;
    int health;
    boolean tangible;

    public Entity(int x, int y, double width, double height, int health, boolean tangible) {
        this.xProperty().set(x);
        this.yProperty().set(y);
        this.setHeight(height);
        this.setWidth(width);
        this.health = health;
        this.tangible = tangible;
    }

    public abstract void takeDamage(int damage);

    public void draw(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        if(sprite != null) gc.drawImage(sprite, getX(), getY());
        else gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public boolean intersects(Entity other){
        return this.intersects(other.getBoundsInLocal());
    }
}
