package com.example.nuclearthrone.model.entity.item;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.entity.AnimationType;
import com.example.nuclearthrone.model.entity.Bullet;
import com.example.nuclearthrone.model.entity.PlayerBullet;
import com.example.nuclearthrone.model.level.Level;
import javafx.scene.image.Image;

public class Slingshot extends Weapon {

    public Slingshot(double x, double y) {
        super(x,y,20,40);
        String uri = "file:" + App.class.getResource("entities/slingshot.png").getPath();
        this.sprite = new Image(uri,20,40,false,true,false);
    }

    @Override
    public AnimationType attack(double x, double y) {
        Bullet bullet = new PlayerBullet(20, 40, 1, 10, Level.getSelected());
        bullet.setVisible(false);
        bullet.shootTo(x, y,1200);
        Level.currentLevel().bullets.add(bullet);
        return AnimationType.SHOOT;
    }
}
