package com.example.nuclearthrone.model.entity.enemy;

import java.util.Objects;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.AnimationType;
import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.IAnimation;
import com.example.nuclearthrone.model.entity.enviroment.Wall;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.util.Direction;
import com.example.nuclearthrone.model.util.Vector;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;

public abstract class Enemy extends Entity implements IAnimation {

    public static final double DISTANCE_IDLE = 70;

    int speed;
    int level;
    Direction lookingAt;
    AnimationType animation;
    Timeline timeline;
    int spriteStage = 0;
    Thread thread = new Thread(this::run);
    double targetX;
    double targetY;
    int stuck = 0;

    public Enemy(double x, double y, double width, double height, int health, int damage, int speed, int level) {
        super(x, y, width, height, health, true);
        this.damage = damage;
        this.speed = speed;
        this.level = level;
        targetX = -1;
        targetY = -1;
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

    public void stop() {
        thread.interrupt();
    }

    @Override
    public void takeDamage(Entity other) {
        health -= other.damage;
        if (health <= 0) {
            isAlive = false;
            Objects.requireNonNull(Level.getLevel(level)).enemies.remove(this);
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

    @SuppressWarnings("BusyWait")
    private void run() {
        while (isAlive) {
            try {
                Thread.sleep(50);
                Platform.runLater(() -> {
                    if (level == Level.getSelected()) {
                        double oldX = getX();
                        double oldY = getY();
                        movement();
                        checkCollisions(oldX, oldY);
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void movement() {
        if (isEntityVisible(Avatar.getInstance())) {
            resetMovement();
            attack(Avatar.getInstance());
        } else {
            initMovement();
            moveTo(targetX, targetY, speed);
        }
    }

    public void moveTo(double x, double y, double speed) {
        Vector movement = new Vector(x - getX(), y - getY());
        movement.normalize();
        movement.setMag(speed / 3);
        setX(getX() + movement.x);
        setY(getY() + movement.y);
    }

    private void checkCollisions(double oldX, double oldY) {
        if (isOutOfScreen(this)) {
            setX(oldX);
            setY(oldY);
            resetMovement();
        } else {
            for (Wall wall : Objects.requireNonNull(Level.getLevel(level)).walls) {
                if (intersects(wall) && !checkStuck(oldX, oldY, wall)) {
                    setX(oldX);
                    setY(oldY);
                    resetMovement();
                    break;
                }
            }
        }
    }

    public void initMovement() {
        if (targetX == -1 || distanceTo(targetX, targetY) < 10) {
            targetX = getX()
                    + Vector.randNegOrPos() * (Math.random() * DISTANCE_IDLE + DISTANCE_IDLE / 3);
            targetY = getY()
                    + Vector.randNegOrPos() * (Math.random() * DISTANCE_IDLE + DISTANCE_IDLE / 3);
        }
    }

    public void resetMovement() {
        targetX = -1;
    }

    public boolean checkStuck(double oldX, double oldY, Entity entity) {
        if (stuck >= 5) {
            if (entity.getX() < getX()) {
                setX(oldX + speed * 2);
            } else {
                setX( oldX - speed * 2);
            }
            if (entity.getY() < getY()) {
                setY(oldY + speed * 2);
            } else {
                setY(oldY - speed * 2);
            }
            stuck = 0;
            return true;
        }
        stuck++;
        return false;
    }

    public Vector randMovemasent() {
        return new Vector(Math.random() * 20, Math.random() * 20);
    }

    public void moveTowards(Entity entity) {
        Vector movement = Vector.getMovementVector(entity.getX() - getX(), entity.getY() - getY(), speed);
        setX(getX() + movement.x);
        setY(getY() + movement.y);
    }

    public void moveAwayFrom(Entity entity) {
        Vector movement = Vector.getMovementVector(entity.getX() - getX(), entity.getY() - getY(), speed);
        setX(getX() - movement.x);
        setY(getY() - movement.y);
    }

    public static Enemy generateEnemy(int level) {
        int type = (int) (Math.random() * 2);
        switch (type) {
            case 0:
                return new Ghost(Math.random() * (MainMenu.getWidth() - Ghost.WIDTH),
                        Math.random() * (MainMenu.getHeight() - Ghost.HEIGHT), level);
            case 1:
                return new Knight(Math.random() * (MainMenu.getWidth() - Ghost.WIDTH),
                        Math.random() * (MainMenu.getHeight() - Ghost.HEIGHT), level);
            default:
                break;
        }
        return null;
    }
}