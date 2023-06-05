package com.example.nuclearthrone.model.entity;

import java.util.HashMap;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.entity.item.Item;
import com.example.nuclearthrone.model.entity.item.Weapon;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.util.Direction;
import com.example.nuclearthrone.model.util.Images;

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

public class Avatar extends Entity implements IAnimation {

    public static final double SPEED = 1.7;
    public static final double WIDTH = 50;
    public static final double HEIGHT = 50;

    private static HashMap<AnimationType, Image[]> animations;
    private static AnimationType animation;
    private static Direction lookingAt;
    private static Timeline animationPlayer;
    private static int spriteStage = 0;
    private static Avatar instance;
    private static final BooleanBinding keyPressed = KeyboardControl.wPressed.or(KeyboardControl.aPressed).or(
            KeyboardControl.sPressed).or(KeyboardControl.dPressed);

    public static Avatar getInstance() {
        if (instance == null)
            instance = new Avatar(70, 70, WIDTH, HEIGHT);
        return instance;
    }

    public Weapon weapon;
    public Rectangle lifeBar;

    private Avatar(double x, double y, double width, double height) {
        super(x, y, width, height, 100, true);
        keyPressed.addListener(this::onKeyPressed);
        animation = AnimationType.IDLE;
        lookingAt = Direction.RIGHT;
        isAlive = true;
        initAnimation();
        animationPlayer = getAnimation();
        startAnimation();
    }

    public void attack(double x, double y) {
        if (animation != AnimationType.SHOOT && animation != AnimationType.ATTACK && isAlive && weapon != null) {
            if (x > getX()) {
                lookingAt = Direction.RIGHT;
            } else {
                lookingAt = Direction.LEFT;
            }
            animation = weapon.attack(x, y);
            spriteStage = 0;
        }
    }

    public Item collect(Item item) {
        if (item instanceof Weapon) {
            if (weapon == null) {
                weapon = (Weapon) item;
            } else {
                Item temp = weapon;
                weapon = (Weapon) item;
                temp.setX(weapon.getX());
                temp.setY(weapon.getY());
                return temp;
            }
        }
        return null;
    }

    public void collectNearbyItem() {
        for (int i = 0; i < Level.currentLevel().items.size(); i++) {
            Item current = Level.currentLevel().items.get(i);
            if (current.intersects(instance)) {
                Item dropped = instance.collect(current);
                if (dropped != null) {
                    Level.currentLevel().items.set(i, dropped);
                } else {
                    Level.currentLevel().items.remove(i);
                }
                break;
            }
        }
    }

    @Override
    public void takeDamage(Entity other) {
        if (isAlive) {
            animation = AnimationType.HIT;
            health -= other.damage;
            isAlive = health > 0;
            if (!isAlive) {
                animation = AnimationType.DEATH;
                spriteStage = 0;
            }
            updateLifeBar();
        }
    }

    private void updateLifeBar() {
        lifeBar.setWidth(170 * (health / 100.0));
        lifeBar.setFill(Color.DARKGREEN); // Update life's bar
    }

    public void onKeyPressed(ObservableValue<? extends Boolean> observable, Boolean a, Boolean keyPressed) {
        if (animation != AnimationType.SHOOT && animation != AnimationType.ATTACK && isAlive) {
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

    AnimationTimer movement = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if (animation != AnimationType.SHOOT && animation != AnimationType.ATTACK && isAlive) {
                double previousX = getX();
                double previousY = getY();
                if (KeyboardControl.wPressed.get()) {
                    setY(previousY - SPEED);
                }
                if (KeyboardControl.sPressed.get()) {
                    setY(previousY + SPEED);
                }
                if (KeyboardControl.aPressed.get()) {
                    setX(previousX - SPEED);
                    lookingAt = Direction.LEFT;
                }
                if (KeyboardControl.dPressed.get()) {
                    setX(previousX + SPEED);
                    lookingAt = Direction.RIGHT;
                }

                if (isOutOfScreen(instance)) {
                    if (!Level.inGate(instance)) {
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

                if ((getX() != previousX || getY() != previousY) && animation != AnimationType.RUN) {
                    animation = AnimationType.RUN;
                    spriteStage = 0;
                }
            }
        }
    };

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
            if (spriteStage < animations.get(animation).length - 1) {
                spriteStage++;
            } else {
                if (animation != AnimationType.DEATH) {
                    if (animation != AnimationType.IDLE && animation != AnimationType.RUN) {
                        animation = AnimationType.IDLE;
                    }
                    spriteStage = 0;
                } else {
                    stopAnimation();
                }
            }
            sprite = animations.get(animation)[spriteStage];
            if (lookingAt == Direction.LEFT)
                sprite = Images.mirrorImageHorizontally(sprite);
        }));
    }

    public void initAnimation() {
        animations = new HashMap<>();
        animations.put(AnimationType.IDLE, new Image[4]);
        for (int i = 1; i <= 4; i++) {
            String uri = "file:" + MainMenu.getFile("entities/avatar/idle/Hobbit - Idle" + i + ".png").getPath();
            animations.get(AnimationType.IDLE)[i - 1] = new Image(uri, getWidth(), getHeight(), false, true, false);
        }
        animations.put(AnimationType.RUN, new Image[10]);
        for (int i = 1; i <= 10; i++) {
            String uri = "file:" + MainMenu.getFile("entities/avatar/run/Hobbit - run" + i + ".png").getPath();
            animations.get(AnimationType.RUN)[i - 1] = new Image(uri, getWidth(), getHeight(), false, true, false);
        }
        animations.put(AnimationType.SHOOT, new Image[17]);
        for (int i = 1; i <= 17; i++) {
            String uri = "file:" + MainMenu.getFile("entities/avatar/shoot/Hobbit - attack" + i + ".png").getPath();
            animations.get(AnimationType.SHOOT)[i - 1] = new Image(uri, getWidth(), getHeight(), false, true, false);
        }
        animations.put(AnimationType.HIT, new Image[4]);
        for (int i = 1; i <= 4; i++) {
            String uri = "file:" + MainMenu.getFile("entities/avatar/hit/Hobbit - hit" + i + ".png").getPath();
            animations.get(AnimationType.HIT)[i - 1] = new Image(uri, getWidth(), getHeight(), false, true, false);
        }
        animations.put(AnimationType.DEATH, new Image[12]);
        for (int i = 1; i <= 12; i++) {
            String uri = "file:" + MainMenu.getFile("entities/avatar/death/Hobbit - death" + i + ".png").getPath();
            animations.get(AnimationType.DEATH)[i - 1] = new Image(uri, getWidth(), getHeight(), true, false, false);
        }
        animations.put(AnimationType.ATTACK, new Image[13]);
        for (int i = 1; i <= 13; i++) {
            String uri = "file:" + MainMenu.getFile("entities/avatar/attack/Hobbit - block" + i + ".png").getPath();
            animations.get(AnimationType.ATTACK)[i - 1] = new Image(uri, getWidth(), getHeight(), false, true, false);
        }
    }
}
