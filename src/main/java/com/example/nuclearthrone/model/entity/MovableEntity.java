package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.model.entity.enviroment.Wall;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.util.Direction;
import com.example.nuclearthrone.model.util.Vector;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;

import java.util.Objects;

public abstract class MovableEntity extends Entity implements IAnimation {

    public int speed;
    public int level;
    public Timeline timeline;
    public AnimationType animation;
    public Direction lookingAt;
    public int spriteStage = 0;
    public Thread thread = new Thread(this::run);
    public int stuck = 0;
    public MovableEntity(double x, double y, double width, double height, int health,int damage, int speed, int level) {
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

    public abstract void movement();
    public abstract void resetMovement();

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
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

    public void run() {
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

    public void moveToDirection(double x, double y) {
        Vector movement = new Vector(x - getCenterX(), y - getCenterY());
        movement.normalize();
        movement.setMag((int) (speed / 3));
        setX(getX() + movement.x);
        setY(getY() + movement.y);
    }

    public void checkCollisions(double oldX, double oldY) {
        if (isOutOfScreen(this)) {
            setX(oldX);
            setY(oldY);
            stuckAtBorder();
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

    public void moveTowards(Entity entity) {
        Vector movement = Vector.getMovementVector(entity.getCenterX() - getCenterX(), entity.getCenterY() - getCenterY(), speed);
        setX(getX() + movement.x);
        setY(getY() + movement.y);
    }

    public void moveAwayFrom(Entity entity) {
        Vector movement = Vector.getMovementVector(entity.getCenterX() - getCenterX(), entity.getCenterY() - getCenterY(), speed);
        setX(getX() - movement.x);
        setY(getY() - movement.y);
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

    public void stuckAtBorder(){

    }
}
