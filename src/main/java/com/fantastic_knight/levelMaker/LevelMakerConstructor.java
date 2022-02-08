package com.fantastic_knight.levelMaker;

import javafx.scene.image.Image;

public class LevelMakerConstructor {

    public static boolean setCheckActive(boolean b, String dossier, String image){
        Image imagePlatform = new Image("file:src/main/resources/com/fantastic_knight/assets/platform.png");

        if (!b) LevelMaker.currentImg = new Image("file:src/main/resources/com/fantastic_knight/" + dossier + "/" + image + ".png");
        else LevelMaker.currentImg = imagePlatform;

        b = !b;
        return b;
    }
}
