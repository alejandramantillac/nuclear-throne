package com.example.nuclearthrone.model.entity.enviroment;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.enemy.EnemyBullet;
import com.example.nuclearthrone.model.level.Level;
import javafx.scene.image.Image;

public class Wall extends Entity {

    int level;
    public static final double WIDTH = 50;
    public static final double HEIGHT = 50;
    public Wall(double x, double y, int health, int level,String spriteName) {
        super(x, y, WIDTH, HEIGHT, health, true);
        this.level = level;
        sprite = getSprite(spriteName);
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

    public Image getSprite(String name){
        String uri = "file:" + App.class.getResource("enviroment/wall/"+name+".png").getPath();
        return new Image(uri,getWidth(),getHeight(),true,false,false);
    }
}
