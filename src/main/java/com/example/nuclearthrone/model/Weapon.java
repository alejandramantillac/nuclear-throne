package com.example.nuclearthrone.model;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;

public abstract class Weapon {

    IntegerProperty ammo;
    double range;
    double damage;
    Image sprite;

    public Weapon(IntegerProperty ammo, double range, double damage, Image sprite) {
        this.ammo = ammo;
        this.range = range;
        this.damage = damage;
        this.sprite = sprite;
    }

    public abstract void attack();

    public boolean hasAmmo() {
        return ammo.get() > 0;
    }

    public void consumeAmmo() {
        if (hasAmmo()) {
            ammo.set(ammo.get() - 1);
        }
    }
}