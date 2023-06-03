package com.example.nuclearthrone.model.entity;

public class PlayerBullet extends Bullet{
    public PlayerBullet(double width, double height, int health, int damage) {
        super(Avatar.getIntance(),width, height, health, damage);
        speed = 5;
    }
}
