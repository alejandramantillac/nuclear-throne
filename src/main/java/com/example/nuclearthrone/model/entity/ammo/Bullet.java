package com.example.nuclearthrone.model.entity.ammo;

import java.util.Objects;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.IAnimation;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.util.Images;
import com.example.nuclearthrone.model.util.Vector;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public abstract class Bullet extends Entity implements IAnimation {

    public static final int DAMAGE = 40;

    double speed;
    Vector movement;
    double initialX;
    double initialY;
    boolean alive;
    int level;
    Timeline animationPlayer;
    Image[] animation;
    int spriteStage;

    public Bullet(Entity entity, double width, double height, int health, int level) {
        super(entity.getX() + (entity.getWidth() / 2), entity.getY() + (entity.getHeight() / 2), width, height, health,true);
        this.damage = DAMAGE;
        this.level = level;
        initialX = getX();
        initialY = getY();
        alive = true;
        initAnimation();
        animationPlayer = getAnimation();
        startAnimation();
    }

    public abstract void initAnimation();

    @Override
    public void takeDamage(Entity other){}

    public void shootTo(double x, double y, double delay) {
        movement = new Vector(x - getX(), y - getY());
        movement.normalize();
        movement.setMag(speed);
        sprite = Images.rotateImage(sprite, Math.toDegrees(movement.angle));
        Thread t = new Thread(() -> {
            try {
                Thread.sleep((long) delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setVisible(true);
            while (alive) {
                setX(getX() + movement.x);
                setY(getY() + movement.y);
                alive = !isOutOfScreen(this);
                if (this instanceof Fireball) {
                    alive = Math.sqrt(Math.pow(initialX - getX(), 2) + Math.pow(initialY - getY(), 2)) <= Fireball.RANGE;
                }
                try {
                    Thread.sleep(MainMenu.msRate());
                } catch (InterruptedException e) {}
            }
            Objects.requireNonNull(Level.getLevel(level)).bullets.remove(this);
        });
        t.start();
    }

    @Override
    public void startAnimation() {
        animationPlayer.setCycleCount(Animation.INDEFINITE);
        animationPlayer.play();
    }

    @Override
    public void stopAnimation() {
        animationPlayer.stop();
    }

    @Override
    public Timeline getAnimation() {
        return new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            if (spriteStage < animation.length - 1) {
                spriteStage++;
            } else {
                spriteStage = 0;
            }
            sprite = animation[spriteStage];
        }));
    }

    public static boolean isOutOfScreen(Entity e) {
        return e.getX() + e.getWidth() - 20 > MainMenu.getWidth() || e.getX() < -20
                || e.getY() + e.getHeight() > MainMenu.getHeight() || e.getY() < -20;
    }
}
