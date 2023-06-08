package com.example.nuclearthrone.model.entity.item;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.enemy.Enemy;
import com.example.nuclearthrone.model.entity.enemy.Ghost;
import com.example.nuclearthrone.model.entity.enemy.Knight;

public class Item extends Entity {
    
    public Item(double x, double y, double width, double height) {
        super(x, y, width, height, -1, true);
    }

    @Override
    public void takeDamage(Entity other) {}

    public static Item generateItem(int level) {
        int type = (int) (Math.random() * 1);
        if (type == 0) {
            return new Healing(Math.random() * (MainMenu.getWidth() - Healing.WIDTH),
                    Math.random() * (MainMenu.getHeight() - Healing.HEIGHT));
        }
        return null;
    }
}
