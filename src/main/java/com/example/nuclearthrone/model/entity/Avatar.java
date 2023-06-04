package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.item.Weapon;
import com.example.nuclearthrone.model.level.Level;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;

public class Avatar extends Entity implements IAnimation {

    private static Avatar instance;
    private static HashMap<String,Image[]> animations;

    public static Avatar getIntance() {
        if (instance == null)
            instance = new Avatar(100,100, 50, 50);
        if (instance.health <= 0)
            return null;
        return instance;
    }

    private int spriteStage = 0;
    double speed;
    boolean isAttacking;
    String lookingAt;
    public Weapon weapon;
    public String animationType;

    public Rectangle lifeBar;
    private BooleanBinding keyPressed = KeyboardControl.wPressed.or(KeyboardControl.aPressed).or(
            KeyboardControl.sPressed).or(KeyboardControl.dPressed);

    private Avatar(double x, double y, double width, double height) {
        super(x, y, width, height, 100, true);
        speed = 1.5;
        isAttacking = false;
        health = 100;
        keyPressed.addListener((a, b, c) -> onKeyPressed(a, b, c));
        initSprites();
        animationType = "idle";
        lookingAt = "right";
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

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
        if(spriteStage < animations.get(animationType).length-1){
            spriteStage++;
        }else{
            if(!animationType.equals("idle")&&!animationType.equals("run")) {
                animationType = "idle";
            }
            spriteStage = 0;
        }
        sprite = animations.get(animationType)[spriteStage];
        if(lookingAt.equals("left")) sprite = mirrorImageHorizontally(sprite);
    }));

    @Override
    public void startAnimation() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void stopAnimation() {
        timeline.stop();
    }

    AnimationTimer movement = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if(!animationType.equals("shoot")){
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
                    lookingAt = "left";
                }
                if (KeyboardControl.dPressed.get()) {
                    setX(previousX + speed);
                    lookingAt = "right";
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

                if((getX() != previousX || getY() != previousY) && !animationType.equals("run")){
                    animationType = "run";
                    spriteStage = 0;
                }
            }
        }
    };

    public void onKeyPressed(ObservableValue<? extends Boolean> observable, Boolean a, Boolean keyPressed) {
        if(!animationType.equals("shoot")) {
            if (keyPressed) {
                movement.start();
                animationType = "run";
                spriteStage = 0;
            } else {
                movement.stop();
                animationType = "idle";
                spriteStage = 0;
            }
        }
    }

    public void shoot(double x, double y) {
        if(!animationType.equals("shoot")){
            if(x>getX()){
                lookingAt = "right";
            }else{
                lookingAt = "left";
            }
            animationType = "shoot";
            spriteStage = 0;
            Bullet bullet = new PlayerBullet(20, 20, 1, 10,Level.getSelected());
            bullet.setVisible(false);
            bullet.shootTo(x, y,1200);
            Level.currentLevel().bullets.add(bullet);
        }
    }

    private void initSprites(){
        animations = new HashMap<>();
        animations.put("idle",new Image[4]);
        for (int i = 1; i <=4; i++) {
            String uri = "file:" + App.class.getResource("entities/avatar/idle/Hobbit - Idle"+i+".png").getPath();
            animations.get("idle")[i-1] = new Image(uri,getWidth(),getHeight(),false,true,false);
        }
        animations.put("run",new Image[10]);
        for (int i = 1; i <=10; i++) {
            String uri = "file:" + App.class.getResource("entities/avatar/run/Hobbit - run"+i+".png").getPath();
            animations.get("run")[i-1] = new Image(uri,getWidth(),getHeight(),false,true,false);
        }
        animations.put("shoot",new Image[17]);
        for (int i = 1; i <=17; i++) {
            String uri = "file:" + App.class.getResource("entities/avatar/shoot/Hobbit - attack"+i+".png").getPath();
            animations.get("shoot")[i-1] = new Image(uri,getWidth(),getHeight(),false,true,false);
        }
    }
}
