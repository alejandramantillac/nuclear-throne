package com.example.nuclearthrone;

import java.util.ArrayList;

import com.example.nuclearthrone.model.KeyboardControl;
import com.example.nuclearthrone.model.entity.*;
import com.example.nuclearthrone.model.entity.enemy.Enemy;
import com.example.nuclearthrone.model.entity.enemy.EnemyBullet;
import com.example.nuclearthrone.model.entity.enviroment.Decoration;
import com.example.nuclearthrone.model.entity.enviroment.Wall;
import com.example.nuclearthrone.model.entity.item.Item;
import com.example.nuclearthrone.model.level.Level;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
        Avatar.getInstance().lifeBar = lifeBar;
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(KeyboardControl::onKeyPressed);
        canvas.setOnKeyReleased(KeyboardControl::onKeyReleased);
        canvas.setOnMousePressed(KeyboardControl::onMousePressed);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Rectangle2D rect = Screen.getPrimary().getBounds();
        canvas.setWidth(rect.getWidth());
        canvas.setHeight(rect.getHeight());
        String uri = "file:" + App.class.getResource("enviroment/decoration/floor.png").getPath();
        Image image = new Image(uri,rect.getWidth(), rect.getHeight(),false,false,false);
        Thread thread = new Thread(() -> {
            while (0 != 1) {
                Platform.runLater(()->{
                    Level current = Level.currentLevel();
                    gc.setFill(Color.BLACK);
                    gc.drawImage(image,0,0);
                    for (Wall wall : current.walls) {
                        wall.draw(gc);
                    }
                    for (Decoration decoration : current.decorations) {
                        decoration.draw(gc);
                    }
                    for (Enemy enemy : current.enemies) {
                        enemy.draw(gc);
                    }
                    for (int i = 0; i < current.items.size(); i++) {
                        Item item = current.items.get(i);
                        item.draw(gc);
                        if(item.intersects(Avatar.getInstance())){
                            Avatar.getInstance().collect(item);
                            current.items.remove(i);
                            i--;
                        }
                    }
                    for (int i = 0; i < current.bullets.size(); i++) {
                        Bullet currentB = current.bullets.get(i);
                        currentB.draw(gc);
                        if(currentB instanceof EnemyBullet){
                            if(currentB.intersects(Avatar.getInstance())){
                                Avatar.getInstance().takeDamage(currentB);
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
                    Avatar.getInstance().draw(gc);
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
