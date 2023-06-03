package com.example.nuclearthrone.model.entity;

public class EnemyBullet extends Bullet {
    public EnemyBullet(Entity entity, double width, double height, int health, int damage) {
        super(entity, width, height, health, damage);
        speed = 3;
    }
}
