package com.example.nuclearthrone.model.util;

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

}
