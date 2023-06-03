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
        level2.walls.add(new Wall(0, 0, 90, 70, 100));
        level2.walls.add(new Wall(90, 0, 90, 200, 100));
        level2.walls.add(new Wall(300, 180, 350, 50, 100));
        level2.walls.add(new Wall(300, 180, 50, 150, 100));
        level2.walls.add(new Wall(650, 180, 50, 150, 100));
        level2.walls.add(new Wall(300, 330, 100, 50, 100));
        level2.walls.add(new Wall(600, 330, 100, 50, 100));
        level2.walls.add(new Wall(300, 600, App.getWidth()-300, 100, 10000));
        level2.walls.add(new Wall(300, 500, 80, 100, 100));
        level2.walls.add(new Wall(800, 180, 120, 430, 10000));
        level2.walls.add(new Wall(920, 550, App.getWidth()-920, 100, 10000));
        level1.right = level2;


        Level level3 = new Level();
        level3.walls.add(new Wall(0,600,App.getWidth(),100,10000));
        level3.walls.add(new Wall(400,400,80,200,100));
        level3.walls.add(new Wall(300,400,180,80,100));
        level3.walls.add(new Wall(570,510,250,90,100));
        level3.walls.add(new Wall(630,330,120,180,100));
        level3.walls.add(new Wall(400,400,80,200,100));
        level3.walls.add(new Wall(1000,180,80,80,100));
        level2.right = level3;

        Level level4 = new Level();
        level4.walls.add(new Wall(200,0,100,200,100));
        level4.walls.add(new Wall(200,480,100,200,100));
        level4.walls.add(new Wall(500,220,100,200,100));
        level3.up=level4;
        level4.left=level1;



        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);
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
