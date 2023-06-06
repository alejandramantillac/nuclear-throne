package com.example.nuclearthrone.model.entity;

import java.util.ArrayList;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.ammo.Bullet;
import com.example.nuclearthrone.model.entity.ammo.EnemyBullet;
import com.example.nuclearthrone.model.entity.enemy.Enemy;
import com.example.nuclearthrone.model.entity.enviroment.Wall;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.util.Direction;
import com.example.nuclearthrone.model.util.Vector;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity extends Rectangle {

    public Image sprite;
    public int health;
    public boolean tangible;
    public boolean isAlive;
    public double damage;

    public Entity(double x, double y, double width, double height, int health, boolean tangible) {
        this.xProperty().set(x);
        this.yProperty().set(y);
        this.setHeight(height);
        this.setWidth(width);
        this.health = health;
        this.tangible = tangible;
        this.isAlive = true;
    }

    public abstract void takeDamage(Entity other);

    public double getCenterX(){
        return getX() + getWidth()/2;
    }
    
    public double getCenterY() {
        return getY() + getHeight() / 2;
    }

    public void draw(GraphicsContext gc) {
        if (!this.isVisible()) {
            return;
        }
        if (sprite != null) {
            gc.drawImage(sprite, getX(), getY());
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    public boolean intersects(Entity other) {
        if (!tangible || !other.tangible) {
            return false;
        }
        if ((this instanceof Avatar || this instanceof Enemy) && (other instanceof Bullet || other instanceof Enemy)) {
            return false;
        }
        if (this instanceof EnemyBullet && other instanceof Enemy) {
            return false;
        }
        return this.intersects(other.getBoundsInLocal());
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Entity> intersectsAny(ObservableList<? extends Entity>... list) {
        ArrayList<Entity> intersected = new ArrayList<>();
        for (ObservableList<? extends Entity> entities : list) {
            for (Entity entity : entities) {
                if (intersects(entity)) {
                    intersected.add(entity);
                }
            }
        }
        return intersected;
    }

    public double distanceTo(double x, double y) {
        return Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));
    }

    public boolean isEntityVisible(Entity entity) {
        Level selected = Level.currentLevel();
        for (Wall wall : selected.walls) {
            if (Vector.intersectsLine(wall, (int) getCenterX(), (int) getCenterY(), (int) entity.getCenterX(), (int) entity.getCenterY())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOutOfScreen(Entity e) {
        return e.getX() + e.getWidth() + 10 > MainMenu.getWidth() || e.getX() <= 0
                || e.getY() + 30 + e.getHeight() > MainMenu.getHeight() || e.getY() <= 0;
    }

    public static Direction getSideOut(Entity e) {
        if (e.getX() + e.getWidth() + 10 > MainMenu.getWidth()) {
            return Direction.RIGHT;
        }
        if (e.getX() <= 0) {
            return Direction.LEFT;
        }
        if (e.getY() + 30 + e.getHeight() > MainMenu.getHeight()) {
            return Direction.DOWN;
        }
        if (e.getY() <= 0) {
            return Direction.UP;
        }
        return Direction.NONE;
    }
}
