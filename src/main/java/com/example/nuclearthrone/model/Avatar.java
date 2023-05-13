package com.example.nuclearthrone.model;

import com.example.nuclearthrone.HelloApplication;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Avatar extends Entity implements IAnimation {

    private int spriteStage;
    int speed;
    boolean isAttacking;
    Weapon weapon;

    private BooleanBinding keyPressed = KeyboardControl.wPressed.or(KeyboardControl.aPressed).or(
            KeyboardControl.sPressed).or(KeyboardControl.dPressed);

    public Avatar(int x, int y, double width, double height) {
        super(x, y, width, height, 100);
        speed = 5;
        isAttacking = false;
        keyPressed.addListener(this::onKeyPressed);
    }

    @Override
    public void takeDamage(int damage) {

    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
        if (spriteStage == 1) {
            sprite = new Image(HelloApplication.getFile("PATH-TO-STAGE-1").getAbsolutePath());
            spriteStage = 2;
        } else if (spriteStage == 2) {
            sprite = new Image(HelloApplication.getFile("PATH-TO-STAGE-1").getAbsolutePath());
            spriteStage = 1;
        }
    }));

    @Override
    public void start() {
        timeline.play();
    }

    @Override
    public void stop() {
        timeline.stop();
    }

    public void onKeyPressed(Observable observable){
        if (KeyboardControl.wPressed.get()) {
            y-=speed;
        }

        if (KeyboardControl.sPressed.get()) {
            y+=speed;
        }

        if (KeyboardControl.aPressed.get()) {
            x-=speed;
        }

        if (KeyboardControl.dPressed.get()) {
            x+=speed;
        }
    }
}
