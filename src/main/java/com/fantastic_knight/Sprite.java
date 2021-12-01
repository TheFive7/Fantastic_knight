package com.fantastic_knight;

import com.fantastic_knight.model.Model;

public abstract class Sprite {

    Model model;
    
    public Sprite(Model model) {
	this.model = model;
    }

    public abstract void update();
}
