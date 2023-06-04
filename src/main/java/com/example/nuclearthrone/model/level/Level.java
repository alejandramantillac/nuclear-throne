package com.example.nuclearthrone.model.level;

import java.util.ArrayList;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.entity.AnimationType;
import com.example.nuclearthrone.model.entity.Bullet;
import com.example.nuclearthrone.model.entity.enemy.Enemy;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.enviroment.Decoration;
import com.example.nuclearthrone.model.entity.enviroment.Wall;

import com.example.nuclearthrone.model.entity.item.Item;
import com.example.nuclearthrone.model.entity.item.Slingshot;
import com.example.nuclearthrone.model.entity.item.Weapon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class Level {

    public ObservableList<Bullet> bullets = FXCollections.observableArrayList();
    public ObservableList<Wall> walls = FXCollections.observableArrayList();
    public ObservableList<Decoration> decorations = FXCollections.observableArrayList();
    public ObservableList<Enemy> enemies = FXCollections.observableArrayList();

    public ObservableList<Item> items = FXCollections.observableArrayList();
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
        Level level1 = initLevel1();
        Level level2 = initLevel2();

        level1.right = level2;
        level2.left = level1;

        levels.add(level1);
        levels.add(level2);

        level1.initializeEnemies();
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

    private static Level initLevel2(){
        Level level = new Level(1);

        level.walls.add(new Wall(200,200,1000,1,"brick"));
        level.walls.add(new Wall(0,50,1000,1,"brick"));
        level.decorations.add(new Decoration(500,500,"floor-shadow-top-right",90));

        return level;
    }
    private static Level initLevel1(){
        Level level = new Level(0);
        //Top of the level
        for (int x = 0; x < App.getWidth(); x+=50) {
            if(x == 850){
                level.walls.add(new Wall(x, 0, 10000,0,"brick-above"));
            }else if( x == 300 || x == 800){
                level.walls.add(new Wall(x, 0, 10000,0,"brick-side-left"));
            }else if(x== 350 || x==900){
                level.walls.add(new Wall(x, 0, 10000,0,"brick-side-right"));
            }else{
                level.walls.add(new Wall(x, 0, 10000,0,"brick"));
            }
        }
        level.walls.add(new Wall(300, 50, 10000,0,"brick-side-left"));
        level.walls.add(new Wall(300, 100, 10000,0,"brick-side-left"));
        level.walls.add(new Wall(350, 50, 10000,0,"brick-side-right"));
        level.walls.add(new Wall(350, 100, 10000,0,"brick-side-right"));
        level.walls.add(new Wall(150, 150, 10000,0,"brick-incorner-top-left"));
        level.walls.add(new Wall(150, 250, 10000,0,"brick-end-left"));
        for (int x = 200; x < 500; x+=50) {
            if(x == 300){
                level.walls.add(new Wall(x, 150, 10000,0,"brick-corner-top-left"));
            }else if(x == 350){
                level.walls.add(new Wall(x, 150, 10000,0,"brick-corner-top-right"));
            }else{
                level.walls.add(new Wall(x, 150, 10000,0,"brick-side-top"));
            }
            level.walls.add(new Wall(x, 200, 10000,0,"brick-side-bottom"));
            level.walls.add(new Wall(x, 250, 10000,0,"brick"));
            level.decorations.add(new Decoration(x,300,"floor-shadow-top-right",0));
        }
        level.walls.add(new Wall(500, 250, 10000,0,"brick-end-right"));
        level.walls.add(new Wall(500, 150, 10000,0,"brick-incorner-top-right"));
        level.walls.add(new Wall(150, 200, 10000,0,"brick-incorner-bottom-left"));
        level.walls.add(new Wall(500, 200, 10000,0,"brick-incorner-bottom-right"));

        // Right side brigde
        for (int x = 800; x < 950; x+=50) {
            for (int y = 50; y < 400; y+=50) {
                if(x == 800){
                    level.walls.add(new Wall(x, y, 10000,0,"brick-side-left"));
                }else if(x == 850){
                    level.walls.add(new Wall(x, y, 10000,0,"brick-above"));
                }else{
                    level.walls.add(new Wall(x, y, 10000,0,"brick-side-right"));
                }
            }
        }

        level.walls.add(new Wall(550, 400, 10000,0,"brick-incorner-top-left"));
        level.walls.add(new Wall(550, 450, 10000,0,"brick-incorner-bottom-left"));
        level.walls.add(new Wall(550, 500, 10000,0,"brick-end-left"));
        for (int x = 600; x < 950; x+=50) {
            if(x == 800){
                level.walls.add(new Wall(x, 400, 10000,0,"brick-corner-top-left"));
                level.walls.add(new Wall(x, 450, 10000,0,"brick-side-bottom"));
            }else if(x == 850){
                level.walls.add(new Wall(x, 400, 10000,0,"brick-above"));
                level.walls.add(new Wall(x, 450, 10000,0,"brick-side-bottom"));
            }else if(x == 900){
                level.walls.add(new Wall(x, 400, 10000,0,"brick-side-right"));
                level.walls.add(new Wall(x, 450, 10000,0,"brick-incorner-bottom-right"));
            }else{
                level.walls.add(new Wall(x, 400, 10000,0,"brick-side-top"));
                level.walls.add(new Wall(x, 450, 10000,0,"brick-side-bottom"));
            }
            level.walls.add(new Wall(x, 500, 10000,0,"brick"));
            level.decorations.add(new Decoration(x,550,"floor-shadow-top-right",0));
        }
        level.walls.add(new Wall(900, 500, 10000,0,"brick-end-right"));

        //Little square at bottom
        level.walls.add(new Wall(150, App.getHeight()-50, 10000,0,"brick-side-left"));
        level.walls.add(new Wall(150, App.getHeight()-100, 10000,0,"brick-side-left"));
        level.walls.add(new Wall(150, App.getHeight()-150, 10000,0,"brick-incorner-top-left"));
        level.walls.add(new Wall(200, App.getHeight()-150, 30,0,"brick-side-top"));
        level.walls.add(new Wall(250, App.getHeight()-150, 30,0,"brick-side-top"));
        level.walls.add(new Wall(300, App.getHeight()-150, 10000,0,"brick-incorner-top-right"));
        level.walls.add(new Wall(300, App.getHeight()-100, 10000,0,"brick-side-right"));
        level.walls.add(new Wall(300, App.getHeight()-50, 10000,0,"brick-side-right"));

        level.items.add(new Slingshot(50,350));

        return level;
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
