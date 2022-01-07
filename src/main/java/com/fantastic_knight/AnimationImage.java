package com.fantastic_knight;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

public class AnimationImage {

    private AnimationTimer timer;
    int length;
    int i = 0;
    Shape shape;
    Image[] images;

    public AnimationImage(Image[] images, Shape shape){
        this.images = images;
        this.shape = shape;
        length = images.length;
        toggleTimer();
    }

    private void toggleTimer() {
        if (timer == null) {
            timer = new AnimationTimer() {
                private final int MAXSAMPPLES = 10;
                private long[] diffs = new long[MAXSAMPPLES];
                private long previousTime = -1;
                private int currentIndex = 0;

                @Override
                public void handle(long now) {
                    if (previousTime != -1) {
                        diffs[currentIndex] = now - previousTime;
                        currentIndex++;
                    }
                    if (currentIndex == MAXSAMPPLES) {
                        shape.setFill(new ImagePattern(images[i]));
                        i++;
                        if (i == length) i=0;

                        currentIndex = 0;
                    }
                    previousTime = now;
                }
            };
            timer.start();
        } else {
            timer.stop();
            timer = null;
        }
    }

    public AnimationTimer getTimer() {
        return timer;
    }
}