package com.example.nuclearthrone.model.level;

import java.util.ArrayList;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.MovableEntity;
import com.example.nuclearthrone.model.entity.ammo.Bullet;
import com.example.nuclearthrone.model.entity.enemy.Enemy;
import com.example.nuclearthrone.model.entity.enviroment.Decoration;
import com.example.nuclearthrone.model.entity.enviroment.Wall;
import com.example.nuclearthrone.model.entity.item.Item;
import com.example.nuclearthrone.model.entity.item.Slingshot;
import com.example.nuclearthrone.model.entity.item.Staff;
import com.example.nuclearthrone.model.entity.npc.Ball;
import com.example.nuclearthrone.model.util.Direction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class Level {

    public static final int MIN_ENEMIES = 1;
    public static final int MAX_ENEMIES = 2;

    public ObservableList<Bullet> bullets = FXCollections.observableArrayList();
    public ObservableList<Wall> walls = FXCollections.observableArrayList();
    public ObservableList<Decoration> decorations = FXCollections.observableArrayList();
    public ObservableList<MovableEntity> entities = FXCollections.observableArrayList();
    public ObservableList<Item> items = FXCollections.observableArrayList();

    public Image background;

    private Level left;
    private Level right;
    private Level up;
    private Level down;

    private final int level;


    private Level(int level) {
        this.level = level;
    }

    private void initializeEnemies() {
        int quantity = (int) (Math.random() * MAX_ENEMIES);
        if(quantity < MIN_ENEMIES) quantity = MIN_ENEMIES;
        while (quantity > 0) {
            Enemy enemy = Enemy.generateEnemy(level);
            if (enemy != null) {
                boolean add = true;
                if(Avatar.getInstance().distanceTo(enemy.getCenterX(),enemy.getCenterY()) < 150){
                    continue;
                }
                if (Entity.isOutOfScreen(enemy)) {
                    continue;
                }
                for (Wall wall : walls) {
                    if (enemy.intersects(wall)) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    entities.add(enemy);
                    quantity--;
                }
            }
        }
    }

    private void initializeItems() {
        int quantity = ((int) (Math.random() * 2));
        while (quantity > 0) {
            Item item = Item.generateItem(level);
            if (item != null) {
                boolean add = true;
                if (Entity.isOutOfScreen(item)) {
                    continue;
                }
                for (Wall wall : walls) {
                    if (item.intersects(wall)) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    items.add(item);
                    quantity--;
                }
            }
        }
    }

    private void start() {
        initializeEnemies();
        initializeItems();
        for (MovableEntity enemy : entities) {
            enemy.start();
        }
    }
    private void destroy(){
        for (MovableEntity enemy : entities) {
            enemy.stop();
        }
    }

    private static ArrayList<Level> levels;
    private static int selected;

    private static void initializeLevels() {
        levels = new ArrayList<>();

        Level level1 = initLevel1();
        Level level2 = initLevel2();
        Level level3 = initLevel3();
        Level level4 = initLevel4();

        level1.right = level2;
        level2.left = level1;
        level2.right = level3;
        level3.left = level2;
        level3.right= level4;
        level4.left = level3;

        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);

        level1.start();
        level3.start();
        level2.start();
        level4.start();

    }

    public static Level currentLevel() {
        if (levels == null)
            initializeLevels();
        return levels.get(selected);
    }

    public static boolean inGate(Entity entity) {
        Direction side = Entity.getSideOut(entity);
        switch (side) {
            case RIGHT -> {
                if (currentLevel().right != null) {
                    selected = levels.indexOf(currentLevel().right);
                    entity.setX(1);
                    return true;
                }
            }
            case LEFT -> {
                if (currentLevel().left != null) {
                    selected = levels.indexOf(currentLevel().left);
                    entity.setX(MainMenu.getWidth() - 11 - entity.getWidth());
                    return true;
                }
            }
            case UP -> {
                if (currentLevel().up != null) {
                    selected = levels.indexOf(currentLevel().up);
                    entity.setY(MainMenu.getHeight() - 30 - entity.getHeight());
                    return true;
                }
            }
            case DOWN -> {
                if (currentLevel().down != null) {
                    selected = levels.indexOf(currentLevel().down);
                    entity.setY(1);
                    return true;
                }
            }
            default -> {
            }
        }
        return false;
    }

    private static Level initLevel1() {
        Level level = new Level(0);

        String uri = "file:" + MainMenu.getFile("environment/decoration/floor.png").getPath();
        level.background = new Image(uri, MainMenu.getWidth(), MainMenu.getHeight(), false, false, false);

        // Top of the level
        for (int x = 0; x < MainMenu.getWidth(); x += 50) {
            if (x == 850) {
                level.walls.add(new Wall(x, 0, 10000, 0, "brick-above"));
            } else if (x == 300 || x == 800) {
                level.walls.add(new Wall(x, 0, 10000, 0, "brick-side-left"));
            } else if (x == 350 || x == 900) {
                level.walls.add(new Wall(x, 0, 10000, 0, "brick-side-right"));
            } else {
                level.walls.add(new Wall(x, 0, 10000, 0, "brick"));
            }
        }
        level.walls.add(new Wall(300, 50, 10000, 0, "brick-side-left"));
        level.walls.add(new Wall(300, 100, 10000, 0, "brick-side-left"));
        level.walls.add(new Wall(350, 50, 10000, 0, "brick-side-right"));
        level.walls.add(new Wall(350, 100, 10000, 0, "brick-side-right"));
        level.walls.add(new Wall(150, 150, 10000, 0, "brick-incorner-top-left"));
        level.walls.add(new Wall(150, 250, 10000, 0, "brick-end-left"));
        for (int x = 200; x < 500; x += 50) {
            if (x == 300) {
                level.walls.add(new Wall(x, 150, 10000, 0, "brick-corner-top-left"));
            } else if (x == 350) {
                level.walls.add(new Wall(x, 150, 10000, 0, "brick-corner-top-right"));
            } else {
                level.walls.add(new Wall(x, 150, 10000, 0, "brick-side-top"));
            }
            level.walls.add(new Wall(x, 200, 10000, 0, "brick-side-bottom"));
            level.walls.add(new Wall(x, 250, 10000, 0, "brick"));
            level.decorations.add(new Decoration(x, 300, "floor-shadow-top-right", 0));
        }
        level.walls.add(new Wall(500, 250, 10000, 0, "brick-end-right"));
        level.walls.add(new Wall(500, 150, 10000, 0, "brick-incorner-top-right"));
        level.walls.add(new Wall(150, 200, 10000, 0, "brick-incorner-bottom-left"));
        level.walls.add(new Wall(500, 200, 10000, 0, "brick-incorner-bottom-right"));

        // Right side bridge
        for (int x = 800; x < 950; x += 50) {
            for (int y = 50; y < 400; y += 50) {
                if (x == 800) {
                    level.walls.add(new Wall(x, y, 10000, 0, "brick-side-left"));
                } else if (x == 850) {
                    level.walls.add(new Wall(x, y, 10000, 0, "brick-above"));
                } else {
                    level.walls.add(new Wall(x, y, 10000, 0, "brick-side-right"));
                }
            }
        }

        level.walls.add(new Wall(550, 400, 10000, 0, "brick-incorner-top-left"));
        level.walls.add(new Wall(550, 450, 10000, 0, "brick-incorner-bottom-left"));
        level.walls.add(new Wall(550, 500, 10000, 0, "brick-end-left"));
        for (int x = 600; x < 950; x += 50) {
            if (x == 800) {
                level.walls.add(new Wall(x, 400, 10000, 0, "brick-corner-top-left"));
                level.walls.add(new Wall(x, 450, 10000, 0, "brick-side-bottom"));
            } else if (x == 850) {
                level.walls.add(new Wall(x, 400, 10000, 0, "brick-above"));
                level.walls.add(new Wall(x, 450, 10000, 0, "brick-side-bottom"));
            } else if (x == 900) {
                level.walls.add(new Wall(x, 400, 10000, 0, "brick-side-right"));
                level.walls.add(new Wall(x, 450, 10000, 0, "brick-incorner-bottom-right"));
            } else {
                level.walls.add(new Wall(x, 400, 10000, 0, "brick-side-top"));
                level.walls.add(new Wall(x, 450, 10000, 0, "brick-side-bottom"));
            }
            level.walls.add(new Wall(x, 500, 10000, 0, "brick"));
            level.decorations.add(new Decoration(x, 550, "floor-shadow-top-right", 0));
        }
        level.walls.add(new Wall(900, 500, 10000, 0, "brick-end-right"));

        // Little square at bottom
        level.walls.add(new Wall(150, MainMenu.getHeight() - 50, 10000, 0, "brick-side-left"));
        level.walls.add(new Wall(150, MainMenu.getHeight() - 100, 10000, 0, "brick-side-left"));
        level.walls.add(new Wall(150, MainMenu.getHeight() - 150, 10000, 0, "brick-incorner-top-left"));
        level.walls.add(new Wall(200, MainMenu.getHeight() - 150, 30, 0, "brick-side-top"));
        level.walls.add(new Wall(250, MainMenu.getHeight() - 150, 30, 0, "brick-side-top"));
        level.walls.add(new Wall(300, MainMenu.getHeight() - 150, 10000, 0, "brick-incorner-top-right"));
        level.walls.add(new Wall(300, MainMenu.getHeight() - 100, 10000, 0, "brick-side-right"));
        level.walls.add(new Wall(300, MainMenu.getHeight() - 50, 10000, 0, "brick-side-right"));

        level.items.add(new Staff(50, 350));
        level.items.add(new Slingshot(200, 350));

        return level;
    }

    private static Level initLevel2() {
        Level level = new Level(1);

        String uri = "file:" + MainMenu.getFile("environment/decoration/road.png").getPath();
        level.background = new Image(uri, MainMenu.getWidth(), MainMenu.getHeight(), false, false, false);

        level.walls.add(new Wall(-45, 0, 10000, 1, "rope"));

        // Water Border
        for (int x = 200; x <= 500; x += 50) {
            level.walls.add(new Wall(x, 75, 10000, 1, "rope"));
            level.walls.add(new Wall(x, 325, 10000, 1, "rope"));
        }
        for (int y = 125; y <= 275; y += 50) {
            level.walls.add(new Wall(200, y, 10000, 1, "rope"));
            level.walls.add(new Wall(500, y, 10000, 1, "rope"));
        }

        // Water
        for (int x = 250; x <= 450; x += 50) {
            for (int y = 125; y <= 275; y += 50) {
                level.walls.add(new Wall(x, y, 10000, 1, "sea1"));
            }
        }

        // Frogs on water
        level.walls.add(new Wall(325, 200, 10000, 1, "frog1"));
        level.walls.add(new Wall(375, 200, 10000, 1, "frog1"));
        level.walls.add(new Wall(325, 250, 10000, 1, "frog1"));
        level.walls.add(new Wall(375, 250, 10000, 1, "frog1"));

        // Cows
        level.walls.add(new Wall(250, 550, 10000, 1, "cow"));
        level.walls.add(new Wall(350, 550, 10000, 1, "cow"));

        // Dog
        level.walls.add(new Wall(700, 20, 10000, 1, "dog"));

        // Pigs
        level.walls.add(new Wall(1000, 200, 10000, 1, "pig"));
        level.walls.add(new Wall(800, 250, 10000, 1, "pig"));

        // Signals
        level.walls.add(new Wall(500, 500, 10000, 1, "signal"));
        level.walls.add(new Wall(800, 200, 10000, 1, "signal"));

        // Shape in the middle right
        for (int x = 825; x <= 1075; x += 50) {
            for (int y = 325; y <= 525; y += 50) {
                level.walls.add(new Wall(x, y, 10000, 1, "rope"));
            }
        }
        for (int y = 375; y <= 475; y += 50) {
            level.walls.add(new Wall(825, y, 10000, 1, "orb-green2"));
            level.walls.add(new Wall(1075, y, 10000, 1, "orb-green2"));
        }
        for (int x = 825; x <= 1075; x += 50) {
            level.walls.add(new Wall(x, 525, 10000, 1, "rope"));
        }
        for (int x = 875; x <= 1025; x += 50) {
            level.walls.add(new Wall(x, 525, 10000, 1, "rope"));
        }

        level.walls.add(new Wall(1260, 0, 10000, 1, "rope"));
        level.walls.add(new Wall(1260, 50, 10000, 1, "rope"));

        return level;
    }
    private static Level initLevel3(){
        Level level = new Level(2);
        String uri = "file:" + MainMenu.getFile("environment/decoration/floor.png").getPath();
        level.background = new Image(uri, MainMenu.getWidth(), MainMenu.getHeight(), false, false, false);

        for(int y=0; y<=300; y+=50){
            for(int x=0;x<=250;x+=50){
                if(y==0){
                    level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                }
                if(x==250){
                    if(y<250){
                        level.walls.add(new Wall(x, y, 10000, 2, "brick-side-right"));
                    }
                    if(y==250){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-bottom-right"));
                    }
                }
                if(y==50){
                    if(x==0||x==50){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-bottom"));
                    }else{
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                    }
                    if(x==250){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-right"));
                    }
                    if(x==100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-corner-bottom-left"));
                    }
                }
                if(x==100 && y>=100 && y<=250){
                    level.walls.add(new Wall(x, y, 10000, 2,"brick-side-left"));
                    if(y==250){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-bottom-left"));
                    }
                }
                if(x>100 && x<250){
                    if(y>=100 && y<250){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                    }
                    if(y==250){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-bottom"));
                    }
                }
                if(y>250){
                    if(x==100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-end-left"));
                    }
                    if(x==250){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-end-right"));
                    }
                    if(x>100 && x<250){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick"));
                    }
                }

            }
        }
        level.decorations.add(new Decoration(0, 100, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(50, 100, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(150, 350, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(200, 350, "floor-shadow-top-right", 0));

        for(int y=470; y<MainMenu.getHeight();y+=50){
            for(int x=500; x<MainMenu.getWidth();x+=50){
                if(y==470){
                    if(x==500){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-top-left"));
                    }
                    if(x==650){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-top-right"));
                    }
                    if(x>500 && x<650){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-top"));
                    }
                }
                if(y==520){
                    if(x==500){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-left"));
                    }
                    if(x==650){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-right"));
                    }
                    if(x>500 && x<650){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                    }
                    if(x==1000){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-left"));
                    }
                    if(x==1100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-right"));
                    }
                    if(x>1000 && x<1100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                    }
                }
                if(y==620 || y==670){
                    if(x==500){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-left"));
                    }else{
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                    }

                }
                if(y==570){
                    if(x==500){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-left"));
                    }
                    if(x>500 && x<650){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                    }
                    if(x==650){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-corner-top-right"));
                    }
                    if(x>650){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-top"));
                    }
                    if(x>1000 && x<1100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                    }
                    if(x==1000){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-corner-top-left"));
                    }
                    if(x==1100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-corner-top-right"));
                    }
                }
            }
        }
        for(int y=250; y<=520; y+=50){
            for(int x=1000; x<1200; x+=50){
                if(y==250){
                    if(x==1000){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-top-left"));
                    }
                    if(x==1100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-top-right"));
                    }
                    if(x>1000 && x<1100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-top"));
                    }
                }
                if(y>250){
                    if(x==1000){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-left"));
                    }
                    if(x==1100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-right"));
                    }
                    if(x>1000 && x<1100){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-above"));
                    }
                }
            }
        }
        for(int y=100; y<400; y+=50){
            for(int x=500; x<=850; x+=50){
                if(y==100){
                    if(x==500){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-top-left"));
                    }
                    if(x==850){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-top-right"));
                    }
                    if(x>500 && x<850){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-top"));
                    }
                }
                if(y>100 && y<300){
                    if(x==500){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-left"));
                    }
                    if(x==850){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-right"));
                    }
                }
                if(y==300){
                    if(x==500 || x==750){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-bottom-left"));
                    }
                    if(x==850 || x==600){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-bottom-right"));
                    }
                    if((x>500 && x<600)||(x<850 && x>750)){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-bottom"));
                    }
                }
                if(y==250){
                    if(x==600){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-top-right"));
                    }
                    if(x==750){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-incorner-top-left"));
                    }
                    if(x==550){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-corner-top-right"));
                    }
                    if(x==800){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-corner-top-left"));
                    }

                }
                if(y==150){
                    if(x>550 && x<800){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-bottom"));
                    }
                    if(x==550){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-corner-bottom-right"));
                    }
                    if(x==800){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-corner-bottom-left"));
                    }
                }
                if(y>300){
                    if(x==500 || x==750){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-end-left"));
                    }
                    if(x==850 || x==600){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-end-right"));
                    }
                    if((x>500 && x<600)||(x>750 && x<850)){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick"));
                    }
                }
                if(y>150 && y<250){
                    if(x==550){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-right"));
                    }
                    if(x==800){
                        level.walls.add(new Wall(x, y, 10000, 2,"brick-side-left"));
                    }
                }
            }
        }
        level.decorations.add(new Decoration(600, 200, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(650, 200, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(700, 200, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(500, 400, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(550, 400, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(750, 400, "floor-shadow-top-right", 0));
        level.decorations.add(new Decoration(800, 400, "floor-shadow-top-right", 0));
        return level;
    }

    private static Level initLevel4() {
        Level level = new Level(3);

        String uri = "file:" + MainMenu.getFile("environment/decoration/soccer-field.jpg").getPath();
        level.background = new Image(uri, MainMenu.getWidth(), MainMenu.getHeight(), false, false, false);

        int columnWidth = 100;
        int columnSpacing = 150;

        level.walls.add(new Wall(-45, 570, 10000, 3, "rope"));
        level.walls.add(new Wall(-45, 620, 10000, 3, "rope"));

        // Left side walls (first column)
        for (int y = 0; y < MainMenu.getHeight(); y += 150) {
            level.walls.add(new Wall(MainMenu.getWidth() / 2 - 2 * columnSpacing - 2 * columnWidth + 50, y,33, 50, columnWidth, 3, "player1"));
        }

        // Left side walls (second column)
        for (int y = 0; y < MainMenu.getHeight(); y += 150) {
            level.walls.add(new Wall(MainMenu.getWidth() / 2 - columnSpacing - columnWidth + 50, y,33, 50, columnWidth, 3, "player1"));
        }

        // Right side walls (first column)
        int rightColumnX = (int) (MainMenu.getWidth() - columnWidth - columnSpacing);
        for (int y = 0; y < MainMenu.getHeight(); y += 150) {
            level.walls.add(new Wall(rightColumnX, y,33, 50, columnWidth, 3, "player2"));
        }

        // Right side walls (second column)
        int secondRightColumnX = rightColumnX - columnWidth - columnSpacing;
        for (int y = 0; y < MainMenu.getHeight(); y += 150) {
            level.walls.add(new Wall(secondRightColumnX, y, 33, 50, columnWidth, 3, "player2"));
        }

        level.entities.add(new Ball(MainMenu.getWidth() / 2 - 25, 325,3));

        return level;
    }


    public static Level getLevel(int level) {
        if (level >= levels.size())
            return null;
        return levels.get(level);
    }

    public static int getSelected() {
        return selected;
    }

    public static void resetLevels(){
        for(Level level :levels){
            level.destroy();
        }
        levels = null;
        selected = 0;
    }
}
