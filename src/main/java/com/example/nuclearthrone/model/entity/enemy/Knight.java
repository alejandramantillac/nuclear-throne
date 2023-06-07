package com.example.nuclearthrone.model.entity.enemy;

import java.util.HashMap;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.AnimationType;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.util.Direction;
import com.example.nuclearthrone.model.util.Images;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Knight extends Enemy {
    
    public static final double ATTACK_RANGE = 40;
    public static final double SECONDS_PER_ATTACK = 3;
    public static final double WIDTH = 50;
    public static final double HEIGHT = 50;
    public static final int HEALTH = 100;
    public static final int SPEED = 4;
    public static final int DAMAGE = 34;
    public static HashMap<AnimationType, Image[]> animations;

    private long nextAttack;

    public Knight(double x, double y, int level) {
        super(x, y, WIDTH, HEIGHT, HEALTH, DAMAGE, SPEED, level);
        nextAttack = System.currentTimeMillis();
        String uri = "file:" + MainMenu.getFile("entities/knight.png").getPath();
        sprite = new Image(uri, getWidth(), getHeight(), true, false, false);
    }

    @Override
    public void attack(Entity entity) {
        double distance = distanceTo(entity.getX(), entity.getY());
        if (distance > ATTACK_RANGE) {
            moveTowards(entity);
        }else if (System.currentTimeMillis() >= nextAttack) {
            hit(entity);
        }
    }

    public void hit(Entity entity){
        nextAttack = System.currentTimeMillis()
                + ((int) ((Math.random() * SECONDS_PER_ATTACK) + SECONDS_PER_ATTACK) * 1000);
        entity.takeDamage(this);
    }

    @Override
    public void initAnimation() {
        if (animations == null) {
            animations = new HashMap<>();
            animations.put(AnimationType.IDLE, new Image[1]);
            String uri = "file:" + MainMenu.getFile("entities/knight.png").getPath();
            animations.get(AnimationType.IDLE)[0] = new Image(uri, getWidth(), getHeight(), false, true, false);
        }
    }

    @Override
    public Timeline getAnimation() {
        return new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            if (spriteStage < animations.get(animation).length - 1) {
                spriteStage++;
            } else {
                if (animation != AnimationType.DEATH) {
                    if (animation != AnimationType.IDLE && animation != AnimationType.RUN) {
                        animation = AnimationType.IDLE;
                    }
                    spriteStage = 0;
                }
            }
            sprite = animations.get(animation)[spriteStage];
            if (lookingAt == Direction.LEFT)
                sprite = Images.mirrorImageHorizontally(sprite);
        }));
    }


}
