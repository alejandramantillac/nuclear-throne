package com.example.nuclearthrone.model.level;

import java.util.ArrayList;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.entity.Bullet;
import com.example.nuclearthrone.model.entity.Enemy;
import com.example.nuclearthrone.model.entity.Wall;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class Level {

    public ObservableList<Bullet> bullets = FXCollections.observableArrayList();
    public ObservableList<Wall> walls = FXCollections.observableArrayList();
    public ObservableList<Enemy> enemies = FXCollections.observableArrayList();
    public Image background;

    private Level() {

    }

    private static ArrayList<Level> levels;
    private static int selected;

    private static void initializeLevels() {
        levels = new ArrayList<>();
        Level level1 = new Level();
        level1.walls.add(new Wall(700, 50, 100, 400, 100));
        level1.walls.add(new Wall(0, 0, App.getWidth(), 50, 10000));
        level1.walls.add(new Wall(300, 50, 50, 100, 100));
        level1.walls.add(new Wall(175, 150, 300, 50, 100));
        level1.walls.add(new Wall(550, 450, 250, 100, 100));
        level1.walls.add(new Wall(175,600,200,200,100));

        levels.add(level1);
    }

    public static Level currentLevel() {
        if (levels == null)
            initializeLevels();
        return levels.get(selected);
    }
}
