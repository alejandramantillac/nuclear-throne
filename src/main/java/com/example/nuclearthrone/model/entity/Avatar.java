package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.HelloApplication;
import com.example.nuclearthrone.HelloController;
import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.item.Weapon;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Avatar extends Entity implements IAnimation {

    private static Avatar instance;

    public static Avatar getIntance() {
        if (instance == null)
            instance = new Avatar(10, 10, 50, 50);
        return instance;
    }

    private int spriteStage;
    int speed;
    boolean isAttacking;
    Weapon weapon;
    private BooleanBinding keyPressed = KeyboardControl.wPressed.or(KeyboardControl.aPressed).or(
            KeyboardControl.sPressed).or(KeyboardControl.dPressed);

    private Avatar(int x, int y, double width, double height) {
        super(x, y, width, height, 100, true);
        speed = 2;
        isAttacking = false;
        keyPressed.addListener((a, b, c) -> onKeyPressed(a, b, c));
    }

    @Override
    public void takeDamage(int damage) {

    }

    public void moveTo(int x, int y) {
        this.xProperty().set(x);
        this.yProperty().set(y);
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
                setY(previousY-speed);
            }
            if (KeyboardControl.sPressed.get()) {
                setY(previousY+speed);
            }
            if (KeyboardControl.aPressed.get()) {
                setX(previousX-speed);
            }
            if (KeyboardControl.dPressed.get()) {
                setX(previousX+speed);
            }
            for (Wall wall : HelloController.walls) {
                if(intersects(wall)){
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

}
