package com.example.nuclearthrone.model.util;

import com.example.nuclearthrone.model.entity.Entity;

public class Vector {

    public double x;
    public double y;
    public double angle;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void normalize() {
        angle = Math.atan2(y, x);
        x = 1 * Math.cos(angle);
        y = 1 * Math.sin(angle);
    }

    public void setMag(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public static Vector getMovementVector(double x, double y, double speed) {
        Vector movement = new Vector(x, y);
        movement.normalize();
        movement.setMag(speed);
        return movement;
    }

    public static int randNegOrPos() {
        return ((int) (Math.random() * 2)) == 1 ? -1 : 1;
    }

    public static boolean intersectsLine(Entity entity, int x1, int y1, int x2, int y2) {
        int left = (int) entity.getX();
        int right = (int) (entity.getX() + entity.getWidth());
        int top = (int) entity.getY();
        int bottom = (int) (entity.getY() + entity.getHeight());

        boolean intersectsLeft = lineIntersectsLine(x1, y1, x2, y2, left, top, left, bottom);
        boolean intersectsRight = lineIntersectsLine(x1, y1, x2, y2, right, top, right, bottom);
        boolean intersectsTop = lineIntersectsLine(x1, y1, x2, y2, left, top, right, top);
        boolean intersectsBottom = lineIntersectsLine(x1, y1, x2, y2, left, bottom, right, bottom);

        return intersectsLeft || intersectsRight || intersectsTop || intersectsBottom;
    }

    private static boolean lineIntersectsLine(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        float denominator = ((x2 - x1) * (y4 - y3)) - ((y2 - y1) * (x4 - x3));

        if (denominator == 0) {
            return false;
        }

        float numerator1 = ((y1 - y3) * (x4 - x3)) - ((x1 - x3) * (y4 - y3));
        float numerator2 = ((y1 - y3) * (x2 - x1)) - ((x1 - x3) * (y2 - y1));

        if (numerator1 == 0 || numerator2 == 0) {
            return false;
        }

        float r = numerator1 / denominator;
        float s = numerator2 / denominator;

        return (r >= 0 && r <= 1) && (s >= 0 && s <= 1);
    }
}
