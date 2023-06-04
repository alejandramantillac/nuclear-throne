package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.Images;
import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.entity.item.Item;
import com.example.nuclearthrone.model.entity.item.Weapon;
import com.example.nuclearthrone.model.level.Level;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;

public class Avatar extends Entity implements IAnimation {

    private static Avatar instance;
    private static HashMap<AnimationType,Image[]> animations;

    public static Avatar getInstance() {
        if (instance == null)
            instance = new Avatar(100,100, 50, 50);
        if (instance.health <= 0)
            return null;
        return instance;
    }

    private int spriteStage = 0;
    double speed;
    String lookingAt;
    public Weapon weapon;
    private AnimationType animation;

    public Rectangle lifeBar;

    private Avatar(double x, double y, double width, double height) {
        super(x, y, width, height, 100, true);
        speed = 1.5;
        BooleanBinding keyPressed = KeyboardControl.wPressed.or(KeyboardControl.aPressed).or(
                KeyboardControl.sPressed).or(KeyboardControl.dPressed);
        keyPressed.addListener(this::onKeyPressed);
        initSprites();
        animation = AnimationType.IDLE;
        lookingAt = "right";
        isAlive = true;
        startAnimation();
    }

    @Override
    public void takeDamage(Entity other) {
        animation = AnimationType.HIT;
        health -= other.damage;
        isAlive = health > 0;
        updateLifeBar();
    }

    private void updateLifeBar(){
        lifeBar.setWidth(170*(health/100.0));
        lifeBar.setFill(Color.DARKGREEN); // Cálculo para cambiar el color de la barra de vida en función del porcentaje de vida
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
        if(spriteStage < animations.get(animation).length-1){
            spriteStage++;
        }else{
            if(animation != AnimationType.DEATH){
                if(animation != AnimationType.IDLE && animation != AnimationType.RUN) {
                    animation = AnimationType.IDLE;
                }
                spriteStage = 0;
            }
        }
        sprite = animations.get(animation)[spriteStage];
        if(lookingAt.equals("left")) sprite = Images.mirrorImageHorizontally(sprite);
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
            if(animation != AnimationType.SHOOT && isAlive){
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

                if((getX() != previousX || getY() != previousY) && animation != AnimationType.RUN){
                    animation = AnimationType.RUN;
                    spriteStage = 0;
                }
            }
        }
    };

    public void onKeyPressed(ObservableValue<? extends Boolean> observable, Boolean a, Boolean keyPressed) {
        if(animation != AnimationType.SHOOT && isAlive) {
            if (keyPressed) {
                movement.start();
                animation = AnimationType.RUN;
            } else {
                movement.stop();
                animation = AnimationType.IDLE;
            }
            spriteStage = 0;
        }
    }

    public void attack(double x, double y) {
        if(animation != AnimationType.SHOOT && isAlive && weapon != null){
            if(x>getX()){
                lookingAt = "right";
            }else{
                lookingAt = "left";
            }
            animation = weapon.attack(x,y);
            spriteStage = 0;
        }
    }

    public void collect(Item item){
        if(item instanceof Weapon){
            if(weapon == null){
                weapon = (Weapon) item;
            }
        }
    }

    private void initSprites(){
        animations = new HashMap<>();
        animations.put(AnimationType.IDLE, new Image[4]);
        for (int i = 1; i <=4; i++) {
            String uri = "file:" + App.class.getResource("entities/avatar/idle/Hobbit - Idle"+i+".png").getPath();
            animations.get(AnimationType.IDLE)[i-1] = new Image(uri,getWidth(),getHeight(),false,true,false);
        }
        animations.put(AnimationType.RUN,new Image[10]);
        for (int i = 1; i <=10; i++) {
            String uri = "file:" + App.class.getResource("entities/avatar/run/Hobbit - run"+i+".png").getPath();
            animations.get(AnimationType.RUN)[i-1] = new Image(uri,getWidth(),getHeight(),false,true,false);
        }
        animations.put(AnimationType.SHOOT,new Image[17]);
        for (int i = 1; i <=17; i++) {
            String uri = "file:" + App.class.getResource("entities/avatar/shoot/Hobbit - attack"+i+".png").getPath();
            animations.get(AnimationType.SHOOT)[i-1] = new Image(uri,getWidth(),getHeight(),false,true,false);
        }
        animations.put(AnimationType.HIT,new Image[4]);
        for (int i = 1; i <=4; i++) {
            String uri = "file:" + App.class.getResource("entities/avatar/hit/Hobbit - hit"+i+".png").getPath();
            animations.get(AnimationType.HIT)[i-1] = new Image(uri,getWidth(),getHeight(),false,true,false);
        }
        animations.put(AnimationType.DEATH,new Image[4]);
        for (int i = 1; i <=4; i++) {
            String uri = "file:" + App.class.getResource("entities/avatar/death/Hobbit - death"+i+".png").getPath();
            animations.get(AnimationType.DEATH)[i-1] = new Image(uri,getWidth(),getHeight(),false,true,false);
        }
    }
}
