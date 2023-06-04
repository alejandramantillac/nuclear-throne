package com.example.nuclearthrone.model.entity;

import java.util.ArrayList;

import com.example.nuclearthrone.App;

import com.example.nuclearthrone.model.entity.enemy.Enemy;
import com.example.nuclearthrone.model.entity.enemy.EnemyBullet;
import com.example.nuclearthrone.model.entity.item.Item;
import javafx.collections.ObservableList;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
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

    public void draw(GraphicsContext gc) {
        if(!this.isVisible())
            return;
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
        if ((this instanceof Avatar || this instanceof Enemy) && (other instanceof Bullet || other instanceof Enemy))
            return false;
        if(this instanceof EnemyBullet && other instanceof Enemy)
            return false;
        return this.intersects(other.getBoundsInLocal());
    }

    public ArrayList<Entity> intersectsAny(ObservableList<? extends Entity>... list) {
        ArrayList<Entity> intersected = new ArrayList<>();
        for(ObservableList<? extends Entity> entities : list){
            for (Entity entity : entities) {
                if(intersects(entity)){
                    intersected.add(entity);
                }
            }
        }
        return intersected;
    }

    public static boolean isOutOfScreen(Entity e) {
        return e.getX() + e.getWidth() +10> App.getWidth() || e.getX() <= 0
                || e.getY() +30+e.getHeight() > App.getHeight() || e.getY()  <= 0;
    }

    public static String getSideOut(Entity e){
        if(e.getX() + e.getWidth() +10> App.getWidth()){
            return "RIGHT";
        }
        if(e.getX() <= 0){
            return "LEFT";
        }
        if(e.getY() +30+e.getHeight() > App.getHeight()){
            return "DOWN";
        }
        if(e.getY()  <= 0){
            return "UP";
        }
        return "NONE";
    }

}
