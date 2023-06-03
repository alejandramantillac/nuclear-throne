package com.example.nuclearthrone.model.entity;

public class RangeEnemy extends Enemy{


    public RangeEnemy(double x, double y, double width, double height, int health, int damage, int speed) {
        super(x, y, width, height, health, damage, speed);
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

    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void kill() {

    }
}
