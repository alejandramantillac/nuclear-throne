package com.example.nuclearthrone;

import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.entity.Wall;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HelloController {

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
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Thread game = new Thread(() -> {
            while(0 != 1){
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                Avatar.getIntance().draw(gc);
                for(Wall wall : walls){
                    wall.draw(gc);
                }
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        game.start();
    }

}
