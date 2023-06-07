package com.example.nuclearthrone.model.entity.item;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.AnimationType;
import com.example.nuclearthrone.model.entity.ammo.Bullet;
import com.example.nuclearthrone.model.entity.ammo.Fireball;
import com.example.nuclearthrone.model.level.Level;

import javafx.scene.image.Image;

public class Staff extends Weapon {

    public static final double DELAY = 600;
    public static final double WIDTH = 30;
    public static final double HEIGHT = 30;
    public static final String SPRITE = "entities/weapon/staff.png";

    public Staff(double x, double y) {
        super(x, y, WIDTH, HEIGHT,DELAY);
        String uri = "file:" + MainMenu.getFile(SPRITE).getPath();
        this.sprite = new Image(uri, WIDTH, HEIGHT, false, true, false);
    }

    @Override
    public AnimationType attack(double x, double y) {
        Bullet bullet = new Fireball(Level.getSelected());
        bullet.setVisible(false);
        bullet.shootTo(x, y, DELAY);
        Level.currentLevel().bullets.add(bullet);
        return AnimationType.ATTACK;
    }

}