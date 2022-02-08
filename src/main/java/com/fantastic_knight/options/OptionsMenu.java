package com.fantastic_knight.options;

import com.fantastic_knight.Game;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

import static com.fantastic_knight.Game.*;

public class OptionsMenu extends Pane {

    public static Pane paneOptionsMenu;
    public static Pane changeSkin;
    public static Pane modifySounds;
    public static Button buttonMenu;
    public static Button buttonSkin;
    public static Button buttonSounds;
    public static ToggleButton previousPlayer;
    public static ToggleButton nextPlayer;
    public static ImageView imageViewPlayer;
    public static List<Image> listSkins;

    public OptionsMenu() {

        // List of the different skins
        listSkins = new ArrayList<>() {
            {
                add(new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/player/perso_droite_idle.png"));
            }
        };

        // Image of the player
        imageViewPlayer = new ImageView(listSkins.get(0));
        imageViewPlayer.setLayoutX(300);
        imageViewPlayer.setLayoutY(125);
        imageViewPlayer.setFitHeight(imageViewPlayer.getImage().getHeight()*5);
        imageViewPlayer.setFitWidth(imageViewPlayer.getImage().getWidth()*5);

        paneOptionsMenu = new Pane();
        paneOptionsMenu.setPrefSize(1200, 800);

        // Button chose the skin
        buttonSkin = new Button("SKINS");
        buttonSkin.setLayoutX(250); buttonSkin.setLayoutY(50);
        buttonSkin.setOnAction(e -> chooseSkinPanel());
        buttonSkin.setPrefSize(150, 50);
        paneOptionsMenu.getChildren().add(buttonSkin);

        // Button modifies the sounds
        buttonSounds = new Button("SOUNDS");
        buttonSounds.setLayoutX(800); buttonSounds.setLayoutY(50);
        buttonSounds.setOnAction(e -> chooseSoundPanel());
        buttonSounds.setPrefSize(150,50);
        paneOptionsMenu.getChildren().add(buttonSounds);

        // Button return to menu
        buttonMenu = new Button();
        buttonMenu.setLayoutX(1050);
        buttonMenu.setLayoutY(650);
        buttonMenu.setOnAction(e -> returnMenu());
        buttonMenu.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/back.png")+"');-fx-background-color: transparent;-fx-background-repeat: no-repeat; -fx-background-size: 100%");
        buttonMenu.setPrefSize(125, 125);
        paneOptionsMenu.getChildren().add(buttonMenu);

        // Adding the panel for the skin customization
        createSkinPane();

        // AJOUTS
        paneOptionsMenu.setStyle("-fx-background-image: url('"+ Game.class.getResource("assets/bg.png")+"')");
        paneOptionsMenu.getChildren().add(changeSkin);
        getChildren().add(paneOptionsMenu);
    }

    private void chooseSkinPanel() {
        System.out.println("\nSkins selected\n");
        paneOptionsMenu.getChildren().clear();
        createSkinPane();
        paneOptionsMenu.getChildren().addAll(changeSkin,buttonMenu,buttonSkin,buttonSounds);
    }

    private void chooseSoundPanel() {
        System.out.println("\nNext selected\n");
        paneOptionsMenu.getChildren().clear();
        createSoundPane();
        paneOptionsMenu.getChildren().addAll(buttonMenu,buttonSkin,buttonSounds);
    }

    private void previousPlayer(ImageView imageViewPlayer, List<Image> listSkins) {
        int index = listSkins.indexOf(imageViewPlayer.getImage());
        if (index == 0) {
            index = listSkins.size() - 1;
        } else {
            index--;
        }
        imageViewPlayer.setImage(listSkins.get(index));
        paneOptionsMenu.getChildren().clear();
        createSkinPane();
        paneOptionsMenu.getChildren().addAll(changeSkin,buttonSkin,buttonSounds,buttonMenu);
    }

    private void nextPlayer(ImageView imageViewPlayer, List<Image> listSkins) {
        int index = listSkins.indexOf(imageViewPlayer.getImage());
        if (index == listSkins.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        imageViewPlayer.setImage(listSkins.get(index));
        paneOptionsMenu.getChildren().clear();
        createSkinPane();
        paneOptionsMenu.getChildren().addAll(changeSkin,buttonSkin,buttonSounds,buttonMenu);
    }

    public void returnMenu() {
        primaryStage.setScene(scene_menu);
    }

    public void createSkinPane(){
        // Skin Panel
        changeSkin = new Pane();
        changeSkin.setPrefSize(800,600);
        changeSkin.setLayoutX(200); changeSkin.setLayoutY(175);
        changeSkin.setStyle("-fx-background-color: #4D3B2A");

        // Button to choose the previous style
        previousPlayer = new ToggleButton();
        previousPlayer.setLayoutX(75); previousPlayer.setLayoutY(450); previousPlayer.setPrefSize(100,100);
        previousPlayer.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/previous.png")+"');-fx-background-color: transparent;-fx-background-repeat: no-repeat");
        previousPlayer.setOnAction(e -> previousPlayer(imageViewPlayer, listSkins));

        // Button to choose the next style
        nextPlayer = new ToggleButton();
        nextPlayer.setLayoutX(650); nextPlayer.setLayoutY(450); nextPlayer.setPrefSize(100,100);
        nextPlayer.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/next.png")+"');-fx-background-color: transparent;-fx-background-repeat: no-repeat");
        nextPlayer.setOnAction(e -> nextPlayer(imageViewPlayer, listSkins));

        changeSkin.getChildren().addAll(imageViewPlayer,previousPlayer,nextPlayer);
    }

    public void createSoundPane(){
        // Skin Panel
        modifySounds = new Pane();
        modifySounds.setPrefSize(800,600);
        modifySounds.setLayoutX(200); modifySounds.setLayoutY(175);
        modifySounds.setStyle("-fx-background-color: red");

        paneOptionsMenu.getChildren().addAll(modifySounds);
    }
}
