package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.item.Weapon;
import com.example.nuclearthrone.model.level.Level;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Avatar extends Entity implements IAnimation {

    private static Avatar instance;
    private static Image[] sprites;

    public static Avatar getIntance() {
        if (instance == null)
            instance = new Avatar(100,100, 50, 50);
        if (instance.health <= 0)
            return null;
        return instance;
    }

    private int spriteStage = 1;
    int speed;
    boolean isAttacking;
    public Weapon weapon;

    public Rectangle lifeBar;
    private BooleanBinding keyPressed = KeyboardControl.wPressed.or(KeyboardControl.aPressed).or(
            KeyboardControl.sPressed).or(KeyboardControl.dPressed);

    private Avatar(double x, double y, double width, double height) {
        super(x, y, width, height, 100, true);
        speed = 2;
        isAttacking = false;
        health = 100;
        keyPressed.addListener((a, b, c) -> onKeyPressed(a, b, c));
        initSprites();
        startAnimation();
    }

    @Override
    public void takeDamage(Entity other) {
        health -= other.damage;
        System.out.print(health);
        updateLifeBar();
    }

    private void updateLifeBar(){
        lifeBar.setWidth(170*(health/100.0));
        lifeBar.setFill(Color.DARKGREEN); // Cálculo para cambiar el color de la barra de vida en función del porcentaje de vida
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
        if (spriteStage == 1) {
            sprite = sprites[0];
            spriteStage = 2;
        } else if (spriteStage == 2) {
            sprite = sprites[0];
            spriteStage = 1;
        }
    }));

    @Override
    public void startAnimation() {
        timeline.play();
    }

    @Override
    public void stopAnimation() {
        timeline.stop();
    }

    AnimationTimer movement = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            double previousX = getX();
            double previousY = getY();
            if (KeyboardControl.wPressed.get()) {
                setY(previousY - speed);
            }
            if (KeyboardControl.sPressed.get()) {
                setY(previousY + speed);
            }
            if (KeyboardControl.aPressed.get()) {
                setX(previousX - speed);
            }
            if (KeyboardControl.dPressed.get()) {
                setX(previousX + speed);
            }

            if(isOutOfScreen(instance)){
                if(!Level.inGate(instance)) {
                    setX(previousX);
                    setY(previousY);
                }
            }

            for (Entity entity : Level.currentLevel().walls) {
                if (intersects(entity)) {
                    setX(previousX);
                    setY(previousY);
                }
            }
        }
    };

    public void onKeyPressed(ObservableValue<? extends Boolean> observable, Boolean a, Boolean keyPressed) {
        if (keyPressed) {
            movement.start();
        } else {
            movement.stop();
        }
    }

    public void shoot(double x, double y) {
        Bullet bullet = new PlayerBullet(20, 20, 1, 10,Level.getSelected());
        bullet.shootTo(x, y);
        Level.currentLevel().bullets.add(bullet);
    }

    private void initSprites(){
        sprites = new Image[1];
        String uri = "file:" + App.class.getResource("entities/avatar.png").getPath();
        sprites[0] = new Image(uri,getWidth(),getHeight(),true,false,false);
    }
}
