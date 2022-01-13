package com.fantastic_knight;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

public class AnimationImage {

    private AnimationTimer timer;
    private final int length;
    private int i = 0;
    private final Shape shape;

    private Image[] images;
    private final int speed;

    public AnimationImage(Image[] images, Shape shape, int speed){
        this.images = images;
        this.shape = shape;
        length = images.length;
        this.speed = speed;
        toggleTimer();
    }

    /**
     * Active l'animation d'images
     */
    private void toggleTimer() {
        if (timer == null) {
            timer = new AnimationTimer() {
                private final int MAXSAMPPLES = speed;
                private final long[] diffs = new long[MAXSAMPPLES];
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

    public void setImages(Image[] images) {
        this.images = images;
    }
}