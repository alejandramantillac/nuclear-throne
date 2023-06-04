package com.example.nuclearthrone.model.entity.item;

import com.example.nuclearthrone.model.entity.AnimationType;
import javafx.scene.image.Image;

public class MeleeWeapon extends Weapon {

    public MeleeWeapon(Image sprite,double x, double y) {
        super(x,y,10,10);
    }

    @Override
    public AnimationType attack(double x, double y) {
        return null;
    }


}