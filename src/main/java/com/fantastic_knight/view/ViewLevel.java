package com.fantastic_knight.view;

import com.fantastic_knight.model.Model;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class ViewLevel {

    Model model;
    Pane pane;
    
    public ViewLevel(Model model, Pane pane) {

		this.model = model;
		this.pane = pane;
		pane.getChildren().clear();

		/*Les bords sont des shapes ? pour que ce soit dans la liste d'obstacles à vérifier ?*/
		// define obstacle for outside the view.
		model.northWall = new Rectangle(0,-50,model.width,50);
		model.southWall = new Rectangle(0,model.height,model.width,50);
		model.westWall = new Rectangle(-50,-50,50,model.height+100);
		model.eastWall = new Rectangle(model.width,-50,50,model.height+100);
		model.obstacles.add(model.northWall);
		model.obstacles.add(model.southWall);
		model.obstacles.add(model.eastWall);
		model.obstacles.add(model.westWall);

		// autocall the init method to setup the level.
		init();
    }

    // to initialize all levels
    public abstract void init();   

}
