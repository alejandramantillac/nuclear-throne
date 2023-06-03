package com.example.nuclearthrone;

import java.util.ArrayList;

import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.entity.*;
import com.example.nuclearthrone.model.level.Level;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class HelloController {

    @FXML
    Canvas canvas;

    @FXML
    private Rectangle lifeBar;

    @SuppressWarnings("unchecked")
    @FXML
    public void initialize(){
        Avatar.getIntance().lifeBar = lifeBar;
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(KeyboardControl::onKeyPressed);
        canvas.setOnKeyReleased(KeyboardControl::onKeyReleased);
        canvas.setOnMousePressed(KeyboardControl::onMousePressed);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Rectangle2D rect = Screen.getPrimary().getBounds();
        canvas.setWidth(rect.getWidth());
        canvas.setHeight(rect.getHeight());
        Thread thread = new Thread(() -> {
            while (0 != 1) {
                Platform.runLater(()->{
                    Level current = Level.currentLevel();
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, rect.getWidth(), rect.getHeight());
                    Avatar.getIntance().draw(gc);
                    for (Wall wall : current.walls) {
                        wall.draw(gc);
                    }
                    for (Enemy enemy : current.enemies) {
                        enemy.draw(gc);
                    }
                    for (int i = 0; i < current.bullets.size(); i++) {
                        Bullet currentB = current.bullets.get(i);
                        currentB.draw(gc);
                        if(currentB instanceof EnemyBullet){
                            if(currentB.intersects(Avatar.getIntance())){
                                Avatar.getIntance().takeDamage(currentB);
                                current.bullets.remove(i);
                                i--;
                                continue;
                            }
                        }
                        ArrayList<Entity> intersected = currentB.intersectsAny(current.walls, current.enemies);
                        for (Entity entity : intersected) {
                            entity.takeDamage(currentB);
                            break;
                        }
                        if (!intersected.isEmpty()) {
                            current.bullets.remove(i);
                            i--;
                        }
                    }
                });
                try {
                    Thread.sleep(App.msRate());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
