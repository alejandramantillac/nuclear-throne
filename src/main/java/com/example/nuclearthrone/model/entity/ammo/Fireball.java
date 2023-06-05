package com.example.nuclearthrone.model.entity.ammo;

import com.example.nuclearthrone.MainMenu;

import javafx.scene.image.Image;

public class Fireball extends PlayerBullet {

    public static final double RANGE = 150;
    public static final double WIDTH = 40;
    public static final double HEIGHT = 40;
    public static final int HITS = 3;

    public Fireball(int level) {
        super(WIDTH, HEIGHT, HITS, level);
        speed = 4;
    }

    @Override
    public void initAnimation() {
        animation = new Image[5];
        for (int i = 1; i <= 5; i++) {
            String uri = "file:" + MainMenu.getFile("entities/ammo/fireball/FB500-" + i + ".png").getPath();
            animation[i - 1] = new Image(uri, WIDTH, HEIGHT, false, true, false);
        }
        sprite = animation[0];
    }

    @Override
    public boolean uniqueAliveConstraint(){
        return Math.sqrt(Math.pow(initialX - getX(), 2) + Math.pow(initialY - getY(), 2)) <= RANGE;
    }
}
