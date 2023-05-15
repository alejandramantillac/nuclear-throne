package com.example.nuclearthrone;

import java.util.ArrayList;

import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.entity.Bullet;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.Wall;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class HelloController {

    public static ObservableList<Bullet> bullets = FXCollections.observableArrayList();
    public static ObservableList<Wall> walls = FXCollections.observableArrayList();
    @FXML
    private Canvas canvas;

    @FXML
    void initialize() {
        walls.add(new Wall(200, 200, 100, 100, 100));
        walls.add(new Wall(200, 100, 100, 30, 100));
        walls.add(new Wall(100, 300, 100, 100, 100));
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(KeyboardControl::onKeyPressed);
        canvas.setOnKeyReleased(KeyboardControl::onKeyReleased);
        canvas.setOnMousePressed(KeyboardControl::onMousePressed);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Rectangle2D rect = Screen.getPrimary().getBounds();
        canvas.setWidth(rect.getWidth());
        canvas.setHeight(rect.getHeight());
        Thread game = new Thread(() -> {
            while (0 != 1) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, rect.getWidth(), rect.getHeight());
                Avatar.getIntance().draw(gc);
                for (Wall wall : walls) {
                    wall.draw(gc);
                }
                for (int i = 0; i < bullets.size();i++) {
                    bullets.get(i).draw(gc);
                    ArrayList<Entity> intersected = bullets.get(i).intersectsAny(walls);
                    for (Entity entity : intersected) {
                        entity.takeDamage(bullets.get(i).damage);
                        break;
                    }
                    if (!intersected.isEmpty()){
                        bullets.remove(i);
                        i--;
                    }
                }
                try {
                    Thread.sleep(HelloApplication.msRate());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        game.start();
    }
}
