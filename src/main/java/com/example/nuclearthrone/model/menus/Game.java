package com.example.nuclearthrone.model.menus;

import java.util.ArrayList;

import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.ammo.Bullet;
import com.example.nuclearthrone.model.entity.ammo.EnemyBullet;
import com.example.nuclearthrone.model.entity.enemy.Enemy;
import com.example.nuclearthrone.model.entity.enviroment.Decoration;
import com.example.nuclearthrone.model.entity.enviroment.Wall;
import com.example.nuclearthrone.model.level.Level;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Game {


    @FXML
    Canvas canvas;

    @FXML
    private Rectangle lifeBar;

    GraphicsContext graphicsContext;

    @FXML
    public void initialize() {
        initKeyBoard();
        initBounds();
        Avatar.getInstance().lifeBar = lifeBar;
        Thread gameThread = new Thread(() -> {
            while (0 != 1) {
                Platform.runLater(() -> {
                    Level currentLevel = Level.currentLevel();
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.drawImage(currentLevel.background, 0, 0);
                    paintEntities(currentLevel);
                    bulletsInteraction(currentLevel);
                    Avatar.getInstance().draw(graphicsContext);
                });
                try {
                    Thread.sleep(msRate());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    public void initKeyBoard() {
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(KeyboardControl::onKeyPressed);
        canvas.setOnKeyReleased(KeyboardControl::onKeyReleased);
        canvas.setOnMousePressed(KeyboardControl::onMousePressed);
    }

    public void initBounds() {
        graphicsContext = canvas.getGraphicsContext2D();
        Rectangle2D rect = Screen.getPrimary().getBounds();
        canvas.setWidth(rect.getWidth());
        canvas.setHeight(rect.getHeight());
    }

    public void paintEntities(Level current) {
        for (Wall wall : current.walls) {
            wall.draw(graphicsContext);
        }
        for (Decoration decoration : current.decorations) {
            decoration.draw(graphicsContext);
        }
        for (Enemy enemy : current.enemies) {
            enemy.draw(graphicsContext);
        }
        for (int i = 0; i < current.items.size(); i++) {
            current.items.get(i).draw(graphicsContext);
        }
    }

    @SuppressWarnings("unchecked")
    public void bulletsInteraction(Level currentLevel){
        for (int i = 0; i < currentLevel.bullets.size(); i++) {
            Bullet currentB = currentLevel.bullets.get(i);
            currentB.draw(graphicsContext);
            if (currentB instanceof EnemyBullet) {
                if (currentB.intersects(Avatar.getInstance())) {
                    Avatar.getInstance().takeDamage(currentB);
                    currentLevel.bullets.remove(i);
                    i--;
                    continue;
                }
            }
            ArrayList<Entity> intersected = currentB.intersectsAny(currentLevel.walls, currentLevel.enemies);
            for (Entity entity : intersected) {
                entity.takeDamage(currentB);
                break;
            }
            if (!intersected.isEmpty()) {
                currentB.health--;
                if (currentB.health <= 0) {
                    currentLevel.bullets.remove(i);
                    i--;
                }
            }
        }
    }
    
    public static int msRate() {
        return 16;
    }
}
