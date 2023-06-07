package com.example.nuclearthrone.model.entity.item;

import com.example.nuclearthrone.model.entity.AnimationType;

public abstract class Weapon extends Item {

    public double delay;
    public Weapon(double x, double y,double width, double height,double delay) {
        super(x,y,width,height);
        this.delay = delay;
    }

    public abstract AnimationType attack(double x, double y);
}