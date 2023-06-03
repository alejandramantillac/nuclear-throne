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
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Avatar extends Entity implements IAnimation {

    private static Avatar instance;

    public static Avatar getIntance() {
        if (instance == null)
            instance = new Avatar(100,100, 50, 50);
        if (instance.health <= 0)
            return null;
        return instance;
    }

    private int spriteStage;
    int speed;
    boolean isAttacking;
    Weapon weapon;
    private BooleanBinding keyPressed = KeyboardControl.wPressed.or(KeyboardControl.aPressed).or(
            KeyboardControl.sPressed).or(KeyboardControl.dPressed);

    private Avatar(double x, double y, double width, double height) {
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
            sprite = new Image(App.getFile("PATH-TO-STAGE-1").getAbsolutePath());
            spriteStage = 2;
        } else if (spriteStage == 2) {
            sprite = new Image(App.getFile("PATH-TO-STAGE-1").getAbsolutePath());
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
        Bullet bullet = new Bullet(20, 10, 1, 10);
        bullet.shootTo(x, y);
        Level.currentLevel().bullets.add(bullet);
    }
}
