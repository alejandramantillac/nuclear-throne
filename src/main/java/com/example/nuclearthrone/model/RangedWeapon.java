package com.example.nuclearthrone.model;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;

public class RangedWeapon extends Weapon {

    public RangedWeapon(IntegerProperty ammo, double range, double damage, Image sprite) {
        super(ammo, range, damage, sprite);
    }

    @Override
    public void attack() {
    }
}
