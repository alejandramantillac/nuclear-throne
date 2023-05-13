package com.example.nuclearthrone.model;


public abstract class Enemy extends Entity implements IAnimation {
    
    int damage;
    int speed;
    
    public Enemy(int x, int y, double width, double height, int health, int damage, int speed) {
        super(x, y, width, height, health);
        this.damage = damage;
        this.speed = speed;
    }
    @Override
    public void start() {
        // TODO: implement animation for enemy
    }
    
    @Override
    public void stop() {
        // TODO: stop enemy animation
    }
    
    public abstract void attack(Entity entity);
}