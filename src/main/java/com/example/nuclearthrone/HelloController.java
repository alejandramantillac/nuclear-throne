package com.example.nuclearthrone;

import java.util.ArrayList;

import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.Wall;
import com.example.nuclearthrone.model.level.Level;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class HelloController {

    @FXML
    Canvas canvas;

    @FXML
    public void initialize(){
        
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
                    for (int i = 0; i < current.bullets.size(); i++) {
                        current.bullets.get(i).draw(gc);
                        ArrayList<Entity> intersected = current.bullets.get(i).intersectsAny(
                                current.walls);
                        for (Entity entity : intersected) {
                            entity.takeDamage(current.bullets.get(i).damage);
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
