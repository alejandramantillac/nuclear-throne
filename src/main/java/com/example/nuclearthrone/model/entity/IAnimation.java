package com.example.nuclearthrone.model.entity;

import javafx.animation.Timeline;

public interface IAnimation {

    void startAnimation();

    void stopAnimation();

    Timeline getAnimation();

    void initAnimation();

}
