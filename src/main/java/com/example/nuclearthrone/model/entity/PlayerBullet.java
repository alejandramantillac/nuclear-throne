package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class PlayerBullet extends Bullet{
    public PlayerBullet(double width, double height, int health, int damage, int level) {
        super(Avatar.getIntance(),width, height, health, damage,level);
        speed = 5;
        String uri = "file:" + App.class.getResource("entities/knife.png").getPath();
        sprite = new Image(uri,20,10,false,true,false);

    }
}
