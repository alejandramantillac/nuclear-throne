package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Enemy extends Entity implements IAnimation,Runnable {
    
    int damage;
    int speed;

    public Enemy(double x, double y, double width, double height, int health, int damage, int speed) {
        super(x, y, width, height, health,false);
        this.damage = damage;
        this.speed = speed;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (sprite != null) {
            gc.drawImage(sprite, getX(), getY());
        } else {
            gc.setFill(Color.RED);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
    public abstract void attack(Entity entity);

    public static Enemy generateEnemy(){
        int type = (int) Math.random()*2;
        switch (type){
            case  0:
                return new RangeEnemy(Math.random()* App.getWidth(),Math.random()* App.getHeight(),50,50,100,2,2);
            case  1:
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public void takeDamage(int damage) {
        if(health <= 0) isAlive = false;
    }

    @Override
    public void startAnimation() {

    }

    @Override
    public void stopAnimation() {

    }

    @Override
    public void run() {

    }
    public void pause() {

    }
    public abstract void kill();

}