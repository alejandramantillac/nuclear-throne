package com.example.nuclearthrone.model.entity;

import javafx.animation.Timeline;

public interface IAnimation {

    public void startAnimation();

    public void stopAnimation();

    public Timeline getAnimation();

    public void initAnimation();

}
