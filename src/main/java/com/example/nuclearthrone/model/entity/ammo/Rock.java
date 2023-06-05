package com.example.nuclearthrone.model.entity.ammo;

import com.example.nuclearthrone.MainMenu;

import javafx.scene.image.Image;

public class Rock extends PlayerBullet {

    public static final double WIDTH = 20;
    public static final double HEIGHT = 20;
    public static final String SPRITE = "entities/ammo/rock.png";
    public static final int HITS = 1;

    public Rock(int level) {
        super(WIDTH, HEIGHT, HITS, level);
        speed = 7;
        stopAnimation();
    }

    @Override
    public void initAnimation() {
        animation = new Image[1];
        String uri = "file:" + MainMenu.getFile(SPRITE).getPath();
        animation[0] = new Image(uri, 20, 10, false, true, false);
        sprite = animation[0];
    }
}
