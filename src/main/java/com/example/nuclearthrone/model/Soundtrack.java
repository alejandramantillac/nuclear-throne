package com.example.nuclearthrone.model;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Soundtrack {

    File mainmenu = new File("utils/soundtrack/03_before_the_dawn.mp3");

    String _mainmenu = "file:///"+mainmenu.getAbsolutePath();

    MediaPlayer mediaPlayer;

    public void mainMenuSong() {
        _mainmenu = _mainmenu.replace("\\", "/");
        Media musicFile = new Media(_mainmenu);
        mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.play();
    }

}
