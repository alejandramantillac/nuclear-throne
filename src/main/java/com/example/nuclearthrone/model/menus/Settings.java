package com.example.nuclearthrone.model.menus;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;

public class Settings {

    @FXML
    private ImageView logoImg;

    @FXML
    private ImageView muteImg;

    @FXML
    private ImageView resetImg;

    @FXML
    private Button resetSettingsBtn;

    @FXML
    private AnchorPane root;

    @FXML
    private Slider sliderSound;

    @FXML
    private ImageView soundImg;

    @FXML
    private ToggleButton turnSound;

    private Soundtrack soundtrack;
    private MediaPlayer mediaPlayer;

    @FXML
    private void initialize() {
        soundtrack = Soundtrack.getInstance();
        resetSettingsBtn.setOnAction(event -> resetSettings());
        sliderSound.valueProperty().addListener((observable, oldValue, newValue) -> adjustVolume(newValue.doubleValue()/100));
        turnSound.setOnAction(event -> toggleSound());
        mediaPlayer = soundtrack.getMediaPlayer();
        sliderSound.setValue(mediaPlayer.getVolume());
        turnSound.setSelected(mediaPlayer.getVolume() > 0);

        StringBinding soundButtonText = Bindings.when(turnSound.selectedProperty())
                .then("Sound On")
                .otherwise("Sound Off");

        turnSound.textProperty().bind(soundButtonText);
    }

    private void resetSettings() {
        sliderSound.setValue(50);
        turnSound.setSelected(true);
        adjustVolume(0.5);
    }


    private void adjustVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
            turnSound.setSelected(mediaPlayer.getVolume() > 0);
        }
    }

    private void toggleSound() {
        if (turnSound.isSelected()) {
            // Turn on sound
            if (mediaPlayer == null) {
                soundtrack.mainMenuSong();
            } else {
                mediaPlayer.play();
            }
        } else {
            // Turn off sound
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }
    }
}
