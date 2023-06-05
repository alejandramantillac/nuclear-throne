package com.example.nuclearthrone.model.entity.ammo;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.Entity;

import javafx.scene.image.Image;

public class EnemyBullet extends Bullet {

    public static final double WIDTH = 20;
    public static final double HEIGHT = 20;
    public static final String SPRITE = "entities/ammo/fireball.png";
    public static final int HITS = 1;

    public EnemyBullet(Entity entity, int level) {
        super(entity, WIDTH, HEIGHT, HITS, level);
        speed = 3;
        stopAnimation();
    }

    @Override
    public void initAnimation() {
        animation = new Image[1];
        String uri = "file:" + MainMenu.getFile(SPRITE).getPath();
        animation[0] = new Image(uri, WIDTH, HEIGHT, true, false, false);
        sprite = animation[0];
    }
}
