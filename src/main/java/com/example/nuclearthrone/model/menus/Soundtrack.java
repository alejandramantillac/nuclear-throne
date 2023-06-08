package com.example.nuclearthrone.model.menus;

import java.io.File;
import java.util.HashMap;

import com.example.nuclearthrone.MainMenu;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Soundtrack {

    public static HashMap<String, MediaPlayer> sounds;
    public static HashMap<String, Boolean> isPlaying;

    private Soundtrack() {
    }

    private static Soundtrack instance;

    MediaPlayer mediaPlayer;
    public void reproduceSound(String name){
        if(isPlaying.containsKey(name) && !isPlaying.get(name)){
            MediaPlayer mediaPlayer = sounds.get(name);
            mediaPlayer.play();
            isPlaying.put(name,true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
    }

    public void reproduceSound(String name, boolean loop){
        if(isPlaying.containsKey(name) && !isPlaying.get(name)){
            MediaPlayer mediaPlayer = sounds.get(name);
            mediaPlayer.play();
            isPlaying.put(name,true);
            if(loop) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            }else{
                mediaPlayer.setOnEndOfMedia(()->{
                    sounds.get(name).stop();
                    isPlaying.put(name,false);
                });
            }
        }
    }

    public void setVolumneOf(String name,double volume){
        if(sounds.containsKey(name)){
            MediaPlayer mediaPlayer = sounds.get(name);
            mediaPlayer.setVolume(volume);
        }
    }

    public void stopSound(String name){
        if(isPlaying.containsKey(name)){
            MediaPlayer mediaPlayer = sounds.get(name);
            mediaPlayer.stop();
            isPlaying.put(name, false);
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static Soundtrack getInstance() {
        if (instance == null) {
            instance = new Soundtrack();
            initSounds();
        }
        return instance;
    }

    public static void initSounds(){
         isPlaying =new HashMap<>();
         sounds = new HashMap<>();
         sounds.put("walking_sound", new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/walking_sound.mp3").getAbsolutePath().replace("\\", "/"))));
         sounds.put("footstep_grass",new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/footstep_grass.mp3").getAbsolutePath().replace("\\", "/"))));
         sounds.put("03_before_the_dawn",new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/03_before_the_dawn.mp3").getAbsolutePath().replace("\\", "/"))));
         sounds.put("goal_sound",new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/goal_sound.mp3").getAbsolutePath().replace("\\", "/"))));
         sounds.put("kickball_sound",new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/kickball_sound.mp3").getAbsolutePath().replace("\\", "/"))));
         sounds.put("playerDamage_sound",new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/playerDamage_sound.mp3").getAbsolutePath().replace("\\", "/"))));
         sounds.put("shot_by_slingshot",new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/shot_by_slingshot.mp3").getAbsolutePath().replace("\\", "/"))));
         sounds.put("enemyKilled_sound",new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/enemyKilled_sound.mp3").getAbsolutePath().replace("\\", "/"))));
         sounds.put("gameOver_sound",new MediaPlayer(new Media("file:///" + new File("utils/soundtrack/gameOver_sound.mp3").getAbsolutePath().replace("\\", "/"))));
         isPlaying.put("walking_sound",false);
         isPlaying.put("03_before_the_dawn",false);
         isPlaying.put("goal_sound",false);
         isPlaying.put("footstep_grass",false);
         isPlaying.put("kickball_sound",false);
         isPlaying.put("playerDamage_sound",false);
         isPlaying.put("shot_by_slingshot",false);
         isPlaying.put("enemyKilled_sound",false);
         isPlaying.put("gameOver_sound",false);
    }
}
