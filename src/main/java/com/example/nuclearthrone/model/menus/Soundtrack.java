package com.example.nuclearthrone.model.menus;

import java.io.File;

import com.example.nuclearthrone.MainMenu;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Soundtrack {

    private Soundtrack() {
    }

    private static Soundtrack instance;

    MediaPlayer mediaPlayer;

    public void reproduceSound(String name) {
        File url = new File("utils/soundtrack/"+name+".mp3");
        String file = "file:///" + url.getAbsolutePath().replace("\\", "/");
        Media musicFile = new Media(file);
        mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.play();
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.5);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static Soundtrack getInstance() {
        if (instance == null) {
            instance = new Soundtrack();
        }
        return instance;
    }

}
