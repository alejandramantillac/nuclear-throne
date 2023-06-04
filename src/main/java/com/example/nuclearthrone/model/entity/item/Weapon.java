package com.example.nuclearthrone.model.entity.item;

import com.example.nuclearthrone.model.entity.AnimationType;
import javafx.scene.image.Image;

public abstract class Weapon extends Item {

    public Weapon(double x, double y,double width, double height) {
        super(x,y,width,height);
    }

    public abstract AnimationType attack(double x, double y);

}