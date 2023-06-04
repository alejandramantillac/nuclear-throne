package com.example.nuclearthrone.model.entity.enviroment;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.Images;
import com.example.nuclearthrone.model.entity.Entity;
import javafx.scene.image.Image;

public class Decoration extends Entity {
    public Decoration(double x, double y, String spriteName, double angle) {
        super(x, y,50,50, 0,false);
        sprite = Images.rotateImage(getSprite(spriteName),angle);
    }

    @Override
    public void takeDamage(Entity other) {}

    public Image getSprite(String name){
        String uri = "file:" + App.class.getResource("enviroment/decoration/"+name+".png").getPath();
        return new Image(uri,getWidth(),getHeight(),true,false,false);
    }
}
