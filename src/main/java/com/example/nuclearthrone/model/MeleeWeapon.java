package com.example.nuclearthrone.model;

import javafx.scene.image.Image;

public class MeleeWeapon extends Weapon {

    public MeleeWeapon(double range, double damage, Image sprite) {
        super(null, range, damage, sprite);
    }

    @Override
    public void attack() {
    }
}