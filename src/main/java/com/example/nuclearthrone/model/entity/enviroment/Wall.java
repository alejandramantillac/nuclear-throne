package com.example.nuclearthrone.model.entity.enviroment;

import java.util.Objects;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.ammo.EnemyBullet;
import com.example.nuclearthrone.model.entity.ammo.Fireball;
import com.example.nuclearthrone.model.entity.ammo.Rock;
import com.example.nuclearthrone.model.level.Level;

import javafx.scene.image.Image;

public class Wall extends Entity {

    int level;
    public static final double WIDTH = 50;
    public static final double HEIGHT = 50;

    public Wall(double x, double y, int health, int level, String spriteName) {
        super(x, y, WIDTH, HEIGHT, health, true);
        sprite = getSprite(spriteName);
        this.level = level;
    }

    public Wall(double x, double y, double width, double height, int health, int level, String spriteName) {
        super(x, y, width, height, health, true);
        sprite = getSprite(spriteName);
        this.level = level;
    }

    @Override
    public void takeDamage(Entity other) {
        if (other instanceof Fireball) {
            health -= 10;
        } else if (other instanceof EnemyBullet) {
            health -= 1;
        } else if (other instanceof Rock) {
            health -= 5;
        }
        if (health <= 0) {
            Objects.requireNonNull(Level.getLevel(level)).walls.remove(this);
        }
    }

    public Image getSprite(String name) {
        String uri = "file:" + MainMenu.getFile("environment/wall/" + name + ".png").getPath();
        return new Image(uri, getWidth(), getHeight(), true, false, false);
    }
}
