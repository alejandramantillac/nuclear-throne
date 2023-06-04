package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import javafx.scene.image.Image;

public class PlayerBullet extends Bullet{
    public PlayerBullet(double width, double height, int health, int damage, int level) {
        super(Avatar.getInstance(),width, height, health, damage,level);
        speed = 7;
        String uri = "file:" + App.class.getResource("entities/knife.png").getPath();
        sprite = new Image(uri,20,10,false,true,false);

    }
}
