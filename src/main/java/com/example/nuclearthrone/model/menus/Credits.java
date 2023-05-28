package com.example.nuclearthrone.model.menus;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Credits {
    @FXML
    private ImageView characterImageView;

    public void initialize() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), characterImageView);
        transition.setFromX(0);
        transition.setToX(50);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setAutoReverse(true);

        transition.play();
    }
}
