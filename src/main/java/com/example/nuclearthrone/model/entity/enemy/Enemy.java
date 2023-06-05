package com.example.nuclearthrone.model.entity.enemy;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.AnimationType;
import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.IAnimation;
import com.example.nuclearthrone.model.entity.enviroment.Wall;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.util.Direction;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;

public abstract class Enemy extends Entity implements IAnimation {

    int speed;
    int level;
    Direction lookingAt;
    AnimationType animation;
    Timeline timeline;
    int spriteStage = 0;
    Thread thread = new Thread(this::run);

    public Enemy(double x, double y, double width, double height, int health, int damage, int speed, int level) {
        super(x, y, width, height, health, true);
        this.damage = damage;
        this.speed = speed;
        this.level = level;
        lookingAt = Direction.RIGHT;
        animation = AnimationType.IDLE;
        initAnimation();
        timeline = getAnimation();
        startAnimation();
    }

    public abstract void attack(Entity entity);

    public void start() {
        thread.start();
    }

    @Override
    public void takeDamage(Entity other) {
        health -= other.damage;
        if (health <= 0) {
            isAlive = false;
            Level.getLevel(level).enemies.remove(this);
        }
    }

    @Override
    public void startAnimation() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void stopAnimation() {
        timeline.stop();
    }

    private void run() {
        // movimiento del enemigo
        while (isAlive) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                boolean in = true;
                if (level == Level.getSelected()) {
                    double oldX = getX();
                    double oldY = getY();
                    attack(Avatar.getInstance());
                    if (isOutOfScreen(this)) {
                        setX(oldX);
                        setY(oldY);
                        in = false;
                    }
                    if (in) {
                        for (Wall wall : Level.getLevel(level).walls) {
                            if (intersects(wall)) {
                                setX(oldX);
                                setY(oldY);
                                break;
                            }
                        }
                    }
                }
            });
        }
    }

    public static Enemy generateEnemy(int level) {
        int type = (int) Math.random() * 2;
        switch (type) {
            case 0:
                return new RangeEnemy(Math.random() * (MainMenu.getWidth() - RangeEnemy.WIDTH),
                        Math.random() * (MainMenu.getHeight() - RangeEnemy.HEIGHT), level);
            case 1:
                break;
            default:
                break;
        }
        return null;
    }
}