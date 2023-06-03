package com.example.nuclearthrone.model.entity;

import com.example.nuclearthrone.App;
import com.example.nuclearthrone.model.level.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Enemy extends Entity implements IAnimation {
    
    int speed;
    Thread thread = new Thread(this::run);
    int level;

    public Enemy(double x, double y, double width, double height, int health, int damage, int speed, int level) {
        super(x, y, width, height, health,true);
        this.damage = damage;
        this.speed = speed;
        this.level = level;
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

    public static Enemy generateEnemy(int level){
        int type = (int) Math.random()*2;
        switch (type){
            case  0:
                return new RangeEnemy(Math.random()* (App.getWidth()-RangeEnemy.WIDTH),Math.random()* (App.getHeight()-RangeEnemy.HEIGHT),100,2,2,level);
            case  1:
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public void takeDamage(Entity other) {
        health -= other.damage;
        System.out.print("enemigo a: "+health);
        if(health <= 0) {
            isAlive = false;
            Level.getLevel(level).enemies.remove(this);
        }
    }

    @Override
    public void startAnimation() {

    }

    @Override
    public void stopAnimation() {

    }

    private void run() {
        //movimiento del enemigo
        while (isAlive) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(level == Level.getSelected()){
                double oldX = getX();
                double oldY = getY();
                attack(Avatar.getIntance());
                if(isOutOfScreen(this)){
                    setX(oldX);
                    setY(oldY);
                    continue;
                }
                for(Wall wall : Level.getLevel(level).walls){
                    if(intersects(wall)){
                        setX(oldX);
                        setY(oldY);
                        break;
                    }
                }
            }
        }
    }

    public void start(){
        thread.start();
    }
    public void pause() {

    }
    public abstract void kill();

}