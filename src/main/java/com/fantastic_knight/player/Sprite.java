package com.fantastic_knight.player;

import com.fantastic_knight.model.Model;

public abstract class Sprite {

    protected final Model model;

    public Sprite(Model model) {
        this.model = model;
    }

    public abstract void update();
}
