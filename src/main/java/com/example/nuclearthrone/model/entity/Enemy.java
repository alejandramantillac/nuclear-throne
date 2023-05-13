package com.example.nuclearthrone.model.entity;

public abstract class Enemy extends Entity implements IAnimation {
    
    int damage;
    int speed;
    
    public Enemy(int x, int y, double width, double height, int health, int damage, int speed) {
        super(x, y, width, height, health,false);
        this.damage = damage;
        this.speed = speed;
    }
    @Override
    public void startAnimation() {
        // TODO: implement animation for enemy
    }
    
    @Override
    public void stopAnimation() {
        // TODO: stop enemy animation
    }
    
    public abstract void attack(Entity entity);
}