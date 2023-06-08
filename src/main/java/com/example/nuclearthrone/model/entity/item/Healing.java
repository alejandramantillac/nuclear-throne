package com.example.nuclearthrone.model.entity.item;

import com.example.nuclearthrone.MainMenu;
import javafx.scene.image.Image;

public class Healing extends Item {

    public static final double HEAL_AMOUNT = 34;
    public static final double WIDTH = 30;
    public static final double HEIGHT = 30;
    public static final String SPRITE = "entities/item/healing_potion.png";
    public Healing(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        String uri = "file:" + MainMenu.getFile(SPRITE).getPath();
        this.sprite = new Image(uri, WIDTH,HEIGHT, false, true, false);
    }
}
