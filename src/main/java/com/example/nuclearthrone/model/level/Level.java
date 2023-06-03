package com.example.nuclearthrone.model.level;

import java.util.ArrayList;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.entity.Bullet;
import com.example.nuclearthrone.model.entity.Enemy;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.Wall;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class Level {

    public ObservableList<Bullet> bullets = FXCollections.observableArrayList();
    public ObservableList<Wall> walls = FXCollections.observableArrayList();
    public ObservableList<Enemy> enemies = FXCollections.observableArrayList();
    public Image background;

    private Level left;
    private Level right;
    private Level up;
    private Level down;

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

        Level level2 = new Level();
        level2.walls.add(new Wall(50,50,50,50,100));
        level1.left = level2;
        level2.right = level1;

        levels.add(level1);
        levels.add(level2);
    }

    public static Level currentLevel() {
        if (levels == null)
            initializeLevels();
        return levels.get(selected);
    }

    public static boolean inGate(Entity entity){
        String side = Entity.getSideOut(entity);
        switch (side){
            case "RIGHT":
                if(currentLevel().right != null){
                    selected = levels.indexOf(currentLevel().right);
                    entity.setX(1);
                    return true;
                }
                break;
            case "LEFT":
                if(currentLevel().left != null){
                    selected = levels.indexOf(currentLevel().left);
                    entity.setX(App.getWidth()-11-entity.getWidth());
                    return true;
                }
                break;
            case "UP":
                if(currentLevel().up != null){
                    selected = levels.indexOf(currentLevel().up);
                    entity.setY(App.getHeight()-30-entity.getHeight());
                    return true;
                }
                break;
            case "DOWN":
                if(currentLevel().down != null){
                    selected = levels.indexOf(currentLevel().down);
                    entity.setY(1);
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }
}
