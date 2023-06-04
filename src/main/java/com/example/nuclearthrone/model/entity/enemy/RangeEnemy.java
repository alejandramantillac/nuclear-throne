package com.example.nuclearthrone.model.entity.enemy;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.entity.Bullet;
import com.example.nuclearthrone.model.entity.Entity;
import com.example.nuclearthrone.model.level.Level;
import javafx.scene.image.Image;

public class RangeEnemy extends Enemy{

    public static double WIDTH = 50;
    public static double HEIGHT = 50;
    public static double SECONDS_PER_SHOT = 4;
    public static int BULLET_DAMAGE = 2;

    private long nextShot;
    public RangeEnemy(double x, double y,  int health, int damage, int speed, int level) {
        super(x, y, WIDTH, HEIGHT, health, damage, speed,level);
        nextShot = System.currentTimeMillis();
        String uri = "file:" + App.class.getResource("entities/wizard.png").getPath();
        sprite = new Image(uri,getWidth(),getHeight(),true,false,false);
    }

    @Override
    public void startAnimation() {
        // TODO: implement animation for enemy
    }

    @Override
    public void stopAnimation() {
        // TODO: stop enemy animation
    }

    @Override
    public void attack(Entity entity) {
        setX(getX() + (Math.random() * 10 - 3));
        setY(getY() + (Math.random() * 10 - 3));
        if(System.currentTimeMillis() >= nextShot){
            shoot(entity.getX(),entity.getY());
        }
    }

    @Override
    public void kill() {

    }

    public void shoot(double x, double y) {
        nextShot = System.currentTimeMillis()+((int)(Math.random()*SECONDS_PER_SHOT)*1000);
        Bullet bullet = new EnemyBullet(this, 20, 20, 1, BULLET_DAMAGE,Level.getSelected());
        bullet.shootTo(x, y);
        Level.currentLevel().bullets.add(bullet);
    }
}
