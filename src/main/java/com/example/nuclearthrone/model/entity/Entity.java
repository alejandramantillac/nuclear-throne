package com.example.nuclearthrone.model.entity;

import java.util.ArrayList;

import com.example.nuclearthrone.App;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity extends Rectangle {

    Image sprite;
    int health;
    boolean tangible;

    public Entity(double x, double y, double width, double height, int health, boolean tangible) {
        this.xProperty().set(x);
        this.yProperty().set(y);
        this.setHeight(height);
        this.setWidth(width);
        this.health = health;
        this.tangible = tangible;
    }

    public abstract void takeDamage(int damage);

    public void draw(GraphicsContext gc) {
        if (sprite != null) {
            gc.drawImage(sprite, getX(), getY());
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    public boolean intersects(Entity other) {
        if (!tangible || !other.tangible)
            return false;
        if (this instanceof Avatar && other instanceof Bullet)
            return false;
        return this.intersects(other.getBoundsInLocal());
    }

    public ArrayList<Entity> intersectsAny(ObservableList<? extends Entity> entities) {
        ArrayList<Entity> intersected = new ArrayList<>();
        for (Entity entity : entities) {
            if (!tangible || !entity.tangible)
                continue;
            if (this instanceof Avatar && entity instanceof Bullet)
                continue;
            if (this.intersects(entity.getBoundsInLocal())) {
                intersected.add(entity);
            }
        }
        return intersected;
    }

    public static boolean isOutOfScreen(Entity e) {
        return e.getX() + e.getWidth() +10> App.getWidth() || e.getX() <= 0
                || e.getY() +30+e.getHeight() > App.getHeight() || e.getY()  <= 0;
    }
}
