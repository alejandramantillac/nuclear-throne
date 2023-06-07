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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Game {


    @FXML
    AnchorPane gameOver;

    @FXML
    private Button menuBtn;

    @FXML
    private ImageView playAgainBtn;

    @FXML
    Canvas canvas;

    @FXML
    private Rectangle lifeBar;

    @FXML
    void goToMenu(ActionEvent event) {
        canvas.getScene().getWindow().hide();
    }

    @FXML
    void playAgain(MouseEvent event) {
        System.out.println("BBB");
        menuBtn.setDisable(true);
        playAgainBtn.setDisable(true);
        gameOver.setVisible(false);
        Avatar.resetAvatar();
        Level.resetLevels();
    }

    @FXML
    public void initialize() {
        initKeyBoard();
        initBounds();
        Avatar.lifeBar = lifeBar;
        Thread gameThread = new Thread(() -> {
            while (0 != 1) {
                Platform.runLater(() -> {
                    Level currentLevel = Level.currentLevel();
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.drawImage(currentLevel.background, 0, 0);
                    paintEntities(currentLevel);
                    checkAvatarAlive();
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

    GraphicsContext graphicsContext;

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
        for (int i = 0; i < current.items.size(); i++) {
            current.items.get(i).draw(graphicsContext);
        }
        bulletsInteraction(current);
        Avatar.getInstance().draw(graphicsContext);
        for (Enemy enemy : current.enemies) {
            enemy.draw(graphicsContext);
        }
    }

    @SuppressWarnings("unchecked")
    public void bulletsInteraction(Level currentLevel) {
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

    public void checkAvatarAlive(){
        if(!Avatar.getInstance().isAlive) {
            gameOver.setVisible(true);
            playAgainBtn.setDisable(false);
            menuBtn.setDisable(false);
        }
    }

    public static int msRate() {
        return 16;
    }
}
