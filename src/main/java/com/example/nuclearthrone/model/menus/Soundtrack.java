package com.example.nuclearthrone.model.menus;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Soundtrack {

    private Soundtrack() {
    }

    private static Soundtrack instance;

    File mainmenu = new File("utils/soundtrack/03_before_the_dawn.mp3");

    String _mainmenu = "file:///"+mainmenu.getAbsolutePath();

    MediaPlayer mediaPlayer;

    public void mainMenuSong() {
        _mainmenu = _mainmenu.replace("\\", "/");
        Media musicFile = new Media(_mainmenu);
        mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.play();
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
