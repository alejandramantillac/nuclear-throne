package com.example.nuclearthrone.model.level;

import java.util.ArrayList;

import com.example.nuclearthrone.MainMenu;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.entity.ammo.Bullet;
import com.example.nuclearthrone.model.entity.enemy.Enemy;
import com.example.nuclearthrone.model.entity.enviroment.Decoration;
import com.example.nuclearthrone.model.entity.enviroment.Wall;
import com.example.nuclearthrone.model.entity.item.Item;
import com.example.nuclearthrone.model.entity.item.Slingshot;
import com.example.nuclearthrone.model.entity.item.Staff;
import com.example.nuclearthrone.model.util.Direction;

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

    private final int level;

    private boolean initialized;

    private Level(int level) {
        initialized = false;
        this.level = level;
    }

    private void initializeEnemies() {
        int quantity = ((int) (Math.random() * 3)) + 1;
        while (quantity > 0) {
            Enemy enemy = Enemy.generateEnemy(level);
            if (enemy != null) {
                boolean add = true;
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
                    enemies.add(enemy);
                    quantity--;
                }
            }
        }
    }

    private void start() {
        if (!initialized) {
            initialized = true;
            for (Enemy enemy : enemies) {
                enemy.start();
            }
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
        level2.initializeEnemies();

        levels.get(selected).start();
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
                    currentLevel().start();
                    return true;
                }
            }
            case LEFT -> {
                if (currentLevel().left != null) {
                    selected = levels.indexOf(currentLevel().left);
                    entity.setX(MainMenu.getWidth() - 11 - entity.getWidth());
                    currentLevel().start();
                    return true;
                }
            }
            case UP -> {
                if (currentLevel().up != null) {
                    selected = levels.indexOf(currentLevel().up);
                    entity.setY(MainMenu.getHeight() - 30 - entity.getHeight());
                    currentLevel().start();
                    return true;
                }
            }
            case DOWN -> {
                if (currentLevel().down != null) {
                    selected = levels.indexOf(currentLevel().down);
                    entity.setY(1);
                    currentLevel().start();
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

        String uri = "file:" + MainMenu.getFile("environment/decoration/floor.png").getPath();
        level.background = new Image(uri, MainMenu.getWidth(), MainMenu.getHeight(), false, false, false);

        // Top of the level
        for (int x = 0; x < MainMenu.getWidth(); x += 50) {
                level.walls.add(new Wall(x, 0, 10000, 0, "shore1"));
        }

        // Water Border
        for (int x = 250; x <= 550; x += 50) {
            level.walls.add(new Wall(x, 50, 10000, 0, "rope"));
            level.walls.add(new Wall(x, 300, 10000, 0, "rope"));
        }
        for (int y = 100; y <= 250; y += 50) {
            level.walls.add(new Wall(250, y, 10000, 0, "rope"));
            level.walls.add(new Wall(550, y, 10000, 0, "rope"));
        }

        // Water
        for (int x = 300; x <= 500; x += 50) {
            for (int y = 100; y <= 250; y += 50) {
                level.walls.add(new Wall(x, y, 10000, 0, "sea1"));
            }
        }

        // Frogs
        level.walls.add(new Wall(375, 175, 10000, 0, "frog1"));
        level.walls.add(new Wall(425, 175, 10000, 0, "frog1"));
        level.walls.add(new Wall(375, 225, 10000, 0, "frog1"));
        level.walls.add(new Wall(425, 225, 10000, 0, "frog1"));

        // Additional walls
        for (int y = 400; y <= 800; y += 150) {
            level.walls.add(new Wall(650, y, 10000, 0, "sign"));
        }
        for (int y = 400; y <= 800; y += 150) {
            level.walls.add(new Wall(800, y, 10000, 0, "sign"));
        }

        // Shape in the top right
        for (int x = 925; x <= 1175; x += 50) {
            for (int y = 125; y <= 325; y += 50) {
                level.walls.add(new Wall(x, y, 10000, 0, "rope"));
            }
        }
        for (int y = 175; y <= 275; y += 50) {
            level.walls.add(new Wall(925, y, 10000, 0, "orb-green2"));
            level.walls.add(new Wall(1175, y, 10000, 0, "orb-green2"));
        }
        for (int x = 975; x <= 1125; x += 50) {
            level.walls.add(new Wall(x, 325, 10000, 0, "rope"));
        }

        // Shape in the bottom left
        for (int x = 125; x <= 275; x += 50) {
            level.walls.add(new Wall(x, 625, 10000, 0, "chest-closed"));
        }
        for (int y = 575; y <= 625; y += 50) {
            level.walls.add(new Wall(125, y, 10000, 0, "chest-closed"));
            level.walls.add(new Wall(275, y, 10000, 0, "chest-closed"));
        }

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
}
