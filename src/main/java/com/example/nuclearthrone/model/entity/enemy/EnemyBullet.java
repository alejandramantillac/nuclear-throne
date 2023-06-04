package com.example.nuclearthrone.model.entity.enemy;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.entity.Bullet;
import com.example.nuclearthrone.model.entity.Entity;
import javafx.scene.image.Image;

public class EnemyBullet extends Bullet {
    public EnemyBullet(Entity entity, double width, double height, int health, int damage, int level) {
        super(entity, width, height, health, damage,level);
        speed = 3;
        String uri = "file:" + App.class.getResource("entities/fireball.png").getPath();
        sprite = new Image(uri,getWidth(),getHeight(),true,false,false);
    }
}
