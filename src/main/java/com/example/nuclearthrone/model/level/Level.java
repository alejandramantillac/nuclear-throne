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

    private int level;

    private boolean initialized;

    private Level(int level) {
        initialized = false;
        this.level = level;
    }
    private void initializeEnemies(){
        int quantity = ((int) (Math.random()*3)) +1;
        while(quantity > 0){
            Enemy enemy = Enemy.generateEnemy(level);
            if(enemy != null){
                boolean add = true;
                if(Entity.isOutOfScreen(enemy)){
                    continue;
                }
                for(Wall wall : walls){
                    if(enemy.intersects(wall)){
                        add = false;
                        break;
                    }
                }
                if(add){
                    enemies.add(enemy);
                    quantity--;
                }
            }
        }
        System.out.println(enemies.size());

    }

    private void start(){
        if(!initialized){
            initialized = true;
            for(Enemy enemy : enemies) {
                enemy.start();
            }
        }
    }

    private void pause(){
        for(Enemy enemy : enemies){
            enemy.pause();
        }
    }
    private static ArrayList<Level> levels;
    private static int selected;

    private static void initializeLevels() {
        levels = new ArrayList<>();
        Level level1 = new Level(0);
        level1.walls.add(new Wall(700, 50, 100, 400, 100,0));
        level1.walls.add(new Wall(0, 0, App.getWidth(), 50, 10000,0));
        level1.walls.add(new Wall(300, 50, 50, 100, 100,0));
        level1.walls.add(new Wall(175, 150, 300, 50, 100,0));
        level1.walls.add(new Wall(550, 450, 250, 100, 100,0));
        level1.walls.add(new Wall(175,600,200,200,100,0));

        Level level2 = new Level(1);
        level2.walls.add(new Wall(0, 0, 90, 70, 100,1));
        level2.walls.add(new Wall(90, 0, 90, 200, 100,1));
        level2.walls.add(new Wall(300, 180, 350, 50, 100,1));
        level2.walls.add(new Wall(300, 180, 50, 150, 100,1));
        level2.walls.add(new Wall(650, 180, 50, 150, 100,1));
        level2.walls.add(new Wall(300, 330, 100, 50, 100,1));
        level2.walls.add(new Wall(600, 330, 100, 50, 100,1));
        level2.walls.add(new Wall(300, 600, App.getWidth()-300, 100, 10000,1));
        level2.walls.add(new Wall(300, 500, 80, 100, 100,1));
        level2.walls.add(new Wall(800, 180, 120, 430, 10000,1));
        level2.walls.add(new Wall(920, 550, App.getWidth()-920, 100, 10000,1));


        Level level3 = new Level(2);
        level3.walls.add(new Wall(0,600,App.getWidth(),100,10000,2));
        level3.walls.add(new Wall(400,400,80,200,100,2));
        level3.walls.add(new Wall(300,400,180,80,100,2));
        level3.walls.add(new Wall(570,510,250,90,100,2));
        level3.walls.add(new Wall(630,330,120,180,100,2));
        level3.walls.add(new Wall(400,400,80,200,100,2));
        level3.walls.add(new Wall(1000,180,80,80,100,2));

        Level level4 = new Level(3);
        level4.walls.add(new Wall(200,0,100,200,100,3));
        level4.walls.add(new Wall(200,480,100,200,100,3));
        level4.walls.add(new Wall(500,220,100,200,100,3));

        level1.right = level2;
        level2.left = level1;
        level2.right = level3;
        level3.left = level2;
        level3.up = level4;
        level4.down = level3;

        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);

        for(Level level : levels){
            level.initializeEnemies();
        }

        levels.get(selected).start();
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
                    currentLevel().pause();
                    selected = levels.indexOf(currentLevel().right);
                    entity.setX(1);
                    currentLevel().start();
                    return true;
                }
                break;
            case "LEFT":
                if(currentLevel().left != null){
                    currentLevel().pause();
                    selected = levels.indexOf(currentLevel().left);
                    entity.setX(App.getWidth()-11-entity.getWidth());
                    currentLevel().start();
                    return true;
                }
                break;
            case "UP":
                if(currentLevel().up != null){
                    currentLevel().pause();
                    selected = levels.indexOf(currentLevel().up);
                    entity.setY(App.getHeight()-30-entity.getHeight());
                    currentLevel().start();
                    return true;
                }
                break;
            case "DOWN":
                if(currentLevel().down != null){
                    currentLevel().pause();
                    selected = levels.indexOf(currentLevel().down);
                    entity.setY(1);
                    currentLevel().start();
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    public static Level getLevel(int level){
        if(level >= levels.size())
            return null;
        return levels.get(level);
    }

    public static int getSelected(){
        return selected;
    }
}
