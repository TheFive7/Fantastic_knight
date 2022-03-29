package com.fantastic_knight.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sounds {
    // MEDIA
    public static Media hurtSound = new Media(new File("src/main/resources/com/fantastic_knight/sounds/dead.wav").toURI().toString());
    public static Media backgroundMusic = new Media(new File("src/main/resources/com/fantastic_knight/sounds/level.wav").toURI().toString());
    public static Media creditSound = new Media(new File("src/main/resources/com/fantastic_knight/sounds/credit.wav").toURI().toString());

    // MEDIAPLAYER
    public static MediaPlayer mediaPlayerHurt = new MediaPlayer(hurtSound);
    public static MediaPlayer mediaPlayerBackgroundMusic = new MediaPlayer(backgroundMusic);
    public static MediaPlayer mediaPlayerCredit = new MediaPlayer(creditSound);
}
