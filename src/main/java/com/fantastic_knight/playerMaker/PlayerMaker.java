package com.fantastic_knight.playerMaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PlayerMaker extends Application {

    // Tete 20 * 25
    // Corps 35 * 30
    // Pieds 35 * 20

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {

        int factor = 3;

        String head = "head";
        String body = "body";
        String leg = "leg";

        List<Image> listHead = new ArrayList<>() {
            {
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/head/casque.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/head/head1.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/head/head2.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/head/head3.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/head/head4.png"));
            }
        };

        List<Image> listBody = new ArrayList<>() {
            {
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/body/plastron.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/body/body1.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/body/body2.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/body/body3.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/body/body4.png"));
            }
        };

        List<Image> listLeg = new ArrayList<>() {
            {
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/leg/pantalon.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/leg/leg1.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/leg/leg2.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/leg/leg3.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/playerMaker/leg/leg4.png"));
            }
        };

        // Pane
        Pane panePrincipal = new Pane();
        panePrincipal.setPrefWidth(150 * factor);
        panePrincipal.setPrefHeight(150 * factor);

        // Images
        ImageView imageViewHead = new ImageView(listHead.get(0));
        imageViewHead.setLayoutX(65 * factor);
        imageViewHead.setLayoutY(37 * factor);
        imageViewHead.setFitHeight(imageViewHead.getImage().getHeight() * factor);
        imageViewHead.setFitWidth(imageViewHead.getImage().getWidth() * factor);

        ImageView imageViewBody = new ImageView(listBody.get(0));
        imageViewBody.setLayoutX(57 * factor);
        imageViewBody.setLayoutY(62 * factor);
        imageViewBody.setFitHeight(imageViewBody.getImage().getHeight() * factor);
        imageViewBody.setFitWidth(imageViewBody.getImage().getWidth() * factor);

        ImageView imageViewLeg = new ImageView(listLeg.get(0));
        imageViewLeg.setLayoutX(57 * factor);
        imageViewLeg.setLayoutY(92 * factor);
        imageViewLeg.setFitHeight(imageViewLeg.getImage().getHeight() * factor);
        imageViewLeg.setFitWidth(imageViewLeg.getImage().getWidth() * factor);

        // Head
        Button buttonNextHead = new Button(">");
        buttonNextHead.setLayoutX(120 * factor);
        buttonNextHead.setLayoutY(43 * factor);
        buttonNextHead.setPrefSize(10 * factor, 10 * factor);
        buttonNextHead.setOnAction(e -> next(imageViewHead, listHead));
        Button buttonPrevHead = new Button("<");
        buttonPrevHead.setLayoutX(20 * factor);
        buttonPrevHead.setLayoutY(43 * factor);
        buttonPrevHead.setPrefSize(10 * factor, 10 * factor);
        buttonPrevHead.setOnAction(e -> prev(imageViewHead, listHead));

        // Body
        Button buttonNextBody = new Button(">");
        buttonNextBody.setLayoutX(120 * factor);
        buttonNextBody.setLayoutY(72 * factor);
        buttonNextBody.setPrefSize(10 * factor, 10 * factor);
        buttonNextBody.setOnAction(e -> next(imageViewBody, listBody));
        Button buttonPrevBody = new Button("<");
        buttonPrevBody.setLayoutX(20 * factor);
        buttonPrevBody.setLayoutY(72 * factor);
        buttonPrevBody.setPrefSize(10 * factor, 10 * factor);
        buttonPrevBody.setOnAction(e -> prev(imageViewBody, listBody));

        // Leg
        Button buttonNextLeg = new Button(">");
        buttonNextLeg.setLayoutX(120 * factor);
        buttonNextLeg.setLayoutY(99 * factor);
        buttonNextLeg.setPrefSize(10 * factor, 10 * factor);
        buttonNextLeg.setOnAction(e -> next(imageViewLeg, listLeg));
        Button buttonPrevLeg = new Button("<");
        buttonPrevLeg.setLayoutX(20 * factor);
        buttonPrevLeg.setLayoutY(99 * factor);
        buttonPrevLeg.setPrefSize(10 * factor, 10 * factor);
        buttonPrevLeg.setOnAction(e -> prev(imageViewLeg, listLeg));

        panePrincipal.getChildren().addAll(imageViewHead, imageViewBody, imageViewLeg, buttonNextHead, buttonPrevHead, buttonNextBody, buttonPrevBody, buttonNextLeg, buttonPrevLeg);

        Scene scene = new Scene(panePrincipal, 150 * factor, 150 * factor);

        stage.setTitle("Fantastic Knight");
        stage.setScene(scene);
        stage.show();
    }

    void next(ImageView imageViewHead, List<Image> listHead) {
        int index = listHead.indexOf(imageViewHead.getImage());
        if (index == listHead.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        imageViewHead.setImage(listHead.get(index));
    }

    void prev(ImageView imageViewHead, List<Image> listHead) {
        int index = listHead.indexOf(imageViewHead.getImage());
        if (index == 0) {
            index = listHead.size() - 1;
        } else {
            index--;
        }
        imageViewHead.setImage(listHead.get(index));
    }
}
