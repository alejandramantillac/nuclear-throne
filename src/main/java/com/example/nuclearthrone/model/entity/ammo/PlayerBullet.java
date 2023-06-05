package com.example.nuclearthrone.model.entity.ammo;

import com.example.nuclearthrone.model.entity.Avatar;

public abstract class PlayerBullet extends Bullet {

    public PlayerBullet(double width, double height, int health, int level) {
        super(Avatar.getInstance(), width, height, health, level);
    }
}
