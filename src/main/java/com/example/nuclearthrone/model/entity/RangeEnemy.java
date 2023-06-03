package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.model.level.Level;

public class RangeEnemy extends Enemy{

    public static double WIDTH = 50;
    public static double HEIGHT = 50;
    public static double SECONDS_PER_SHOT = 4;
    public static int BULLET_DAMAGE = 2;

    private long nextShot;
    public RangeEnemy(double x, double y,  int health, int damage, int speed, int level) {
        super(x, y, WIDTH, HEIGHT, health, damage, speed,level);
        nextShot = System.currentTimeMillis();
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
        Bullet bullet = new EnemyBullet(this, 20, 10, 1, BULLET_DAMAGE);
        bullet.shootTo(x, y);
        Level.currentLevel().bullets.add(bullet);
    }
}
