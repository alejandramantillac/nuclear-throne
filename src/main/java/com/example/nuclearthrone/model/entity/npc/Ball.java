package com.example.nuclearthrone.model.entity.npc;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.AnimationType;
import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.MovableEntity;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.util.Direction;
import com.example.nuclearthrone.model.util.Images;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Ball extends MovableEntity {

    public static AnchorPane winner;
    public static final double WIDTH = 30;
    public static final double HEIGHT = 30;
    public static final int HEALTH = -1;
    public static final int SPEED = 4;
    public static final double DISTANCE = 45;
    Image[] sprites;

    public Ball(double x, double y, int level) {
        super(x, y, WIDTH, HEIGHT, HEALTH, 0, SPEED, level);
        animation = AnimationType.IDLE;
    }

    @Override
    public void takeDamage(Entity other) {}

    @Override
    public Timeline getAnimation() {
        return new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            if(animation != AnimationType.IDLE){
                if (spriteStage < sprites.length - 1) {
                    spriteStage++;
                } else {
                    spriteStage = 0;
                }
            }
             sprite = sprites[spriteStage];
             if (lookingAt == Direction.LEFT)
                 sprite = Images.mirrorImageHorizontally(sprite);
        }));
    }

    @Override
    public void initAnimation() {
        sprites = new Image[1];
        sprites[0] = new Image(MainMenu.getFile("environment/wall/ball.png").getPath(),WIDTH,HEIGHT,true,true,false);
    }

    @Override
    public void movement() {
        double distance = distanceTo(Avatar.getInstance().getX(),Avatar.getInstance().getY());
        if(distance < DISTANCE){
            moveAwayFrom(Avatar.getInstance());
            if(intersects(1185,315,30,100) && !Level.enemiesLeft()){
                winner.setVisible(true);
            }
        }
    }

    @Override
    public void resetMovement() {}

    @Override
    public void stuckAtBorder(){
        if (stuck >= 100) {
            setX(MainMenu.getWidth() / 2 - 2);
            setY(325);
            stuck = 0;
        }
        stuck++;
    }
}
