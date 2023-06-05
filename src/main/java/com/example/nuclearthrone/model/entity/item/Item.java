package com.example.nuclearthrone.model.entity.item;

import com.example.nuclearthrone.model.entity.Entity;

public class Item extends Entity {
    
    public Item(double x, double y, double width, double height) {
        super(x, y, width, height, -1, true);
    }

    @Override
    public void takeDamage(Entity other) {}
}
