package com.fantastic_knight.options;

import com.fantastic_knight.Game;
import com.fantastic_knight.menu.ButtonLevel;
import com.fantastic_knight.model.Sounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import static com.fantastic_knight.Game.*;

public class OptionsMenu extends Pane {

    public static Pane paneOptionsMenu;
    public static Pane changeSkin;
    public static Pane modifySounds;
    public static Button buttonMenu;
    public static ButtonLevel buttonSkin;
    public static ButtonLevel buttonSounds;
    public static ToggleButton previousPlayer;
    public static ToggleButton nextPlayer;
    public static ImageView imageViewPlayer;
    public static ImageView imageSound;
    public static ImageView imageSoundOther;
    public static List<Image> listSkins;
    public static int indexSkin;

    public OptionsMenu() {

        // List of the different skins
        listSkins = new ArrayList<>() {
            {
                add(new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right_platine.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right_gold.png"));
                add(new Image("file:src/main/resources/com/fantastic_knight/player/perso_droite_idle.png"));
            }
        };

        // Image of the player
        imageViewPlayer = new ImageView(listSkins.get(0));
        imageViewPlayer.setLayoutX(300);
        imageViewPlayer.setLayoutY(25);
        imageViewPlayer.setFitHeight(imageViewPlayer.getImage().getHeight()*5);
        imageViewPlayer.setFitWidth(imageViewPlayer.getImage().getWidth()*5);

        // Pane general
        paneOptionsMenu = new Pane();
        paneOptionsMenu.setPrefSize(1200, 800);

        // Button chose the skin
        buttonSkin = new ButtonLevel("SKINS",270,60);
        buttonSkin.setOnAction(e -> chooseSkinPanel());
        buttonSkin.setPrefSize(125, 50);
        buttonSkin.setStyle(getStyle() + "-fx-background-image: url('"+ Game.class.getResource("icons/button.png")+"'); -fx-opacity: 0.0");
        paneOptionsMenu.getChildren().add(buttonSkin);

        // Button modifies the sounds
        buttonSounds = new ButtonLevel("SOUNDS",820,65);
        buttonSounds.setOnAction(e -> chooseSoundPanel());
        buttonSounds.setPrefSize(125,50);
        buttonSounds.setStyle(getStyle() + "-fx-background-image: url('"+ Game.class.getResource("icons/button.png")+"'); -fx-opacity: 0.0");
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
        indexSkin=0;
        createSkinPane();

        // Adding all elements
        paneOptionsMenu.setStyle("-fx-background-image: url('"+ Game.class.getResource("menu/options.png")+"')");
        paneOptionsMenu.getChildren().add(changeSkin);
        getChildren().add(paneOptionsMenu);
    }

    private void chooseSkinPanel() {
        paneOptionsMenu.getChildren().clear();
        createSkinPane();
        paneOptionsMenu.getChildren().addAll(changeSkin,buttonMenu,buttonSkin,buttonSounds);
    }

    private void chooseSoundPanel() {
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
        indexSkin = index;
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
        indexSkin = index;
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

        // Button to choose the previous style
        previousPlayer = new ToggleButton();
        previousPlayer.setLayoutX(75); previousPlayer.setLayoutY(300); previousPlayer.setPrefSize(100,100);
        previousPlayer.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/previous.png")+"');-fx-background-color: transparent;-fx-background-repeat: no-repeat");
        previousPlayer.setOnAction(e -> previousPlayer(imageViewPlayer, listSkins));

        // Button to choose the next style
        nextPlayer = new ToggleButton();
        nextPlayer.setLayoutX(650); nextPlayer.setLayoutY(300); nextPlayer.setPrefSize(100,100);
        nextPlayer.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/next.png")+"');-fx-background-color: transparent;-fx-background-repeat: no-repeat");
        nextPlayer.setOnAction(e -> nextPlayer(imageViewPlayer, listSkins));

        // Button to save
        Button saveButton = new ButtonLevel("SAVE",320,475);
        saveButton.setOnAction(e -> settingNamePlayer());
        saveButton.setPrefSize(120,50);
        saveButton.setStyle(getStyle() + "-fx-background-image: url('"+ Game.class.getResource("icons/button.png")+"'); -fx-opacity: 0.0");

        changeSkin.getChildren().addAll(imageViewPlayer,previousPlayer,nextPlayer,saveButton);
    }

    public void createSoundPane(){
        // Skin Panel
        modifySounds = new Pane();
        modifySounds.setPrefSize(800,600);
        modifySounds.setLayoutX(200); modifySounds.setLayoutY(165);

        // Label for background music
        Label backgroundMusic = new Label("- Musique");
        backgroundMusic.setLayoutX(100); backgroundMusic.setLayoutY(50);
        backgroundMusic.setFont(new Font(25));
        backgroundMusic.setStyle("-fx-font-weight: bold; -fx-text-fill: black");

        // Label environnment sounds
        Label environnementSounds = new Label("- Autres");
        environnementSounds.setLayoutX(100); environnementSounds.setLayoutY(300);
        environnementSounds.setFont(new Font(25));
        environnementSounds.setStyle("-fx-font-weight: bold; -fx-text-fill: black");

        // Image sound
        imageSound = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/icons/sound.png"));
        imageSound.setFitHeight(imageSound.getImage().getHeight()*0.15);
        imageSound.setFitWidth(imageSound.getImage().getWidth()*0.15);
        imageSound.setLayoutX(100); imageSound.setLayoutY(125);

        // Vertical slider for background music
        Slider slider = new Slider(0, 100, 50);
        slider.setLayoutX(200); slider.setLayoutY(150);
        slider.setPrefSize(500,50);
        slider.setShowTickMarks(false); slider.setShowTickLabels(false);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.5f);
        slider.setValueChanging(true);
        slider.setOnMouseDragged(e -> {
            modifySounds.getChildren().remove(imageSound);
            chooseIconSound(slider.getValue());
        });

        // Image sound
        imageSoundOther = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/icons/sound.png"));
        imageSoundOther.setFitHeight(imageSoundOther.getImage().getHeight()*0.15);
        imageSoundOther.setFitWidth(imageSoundOther.getImage().getWidth()*0.15);
        imageSoundOther.setLayoutX(100); imageSoundOther.setLayoutY(335);

        // Vertical slider for sounds
        Slider soundsSlider = new Slider(0, 100, 50);
        soundsSlider.setLayoutX(200); soundsSlider.setLayoutY(350);
        soundsSlider.setPrefSize(500,50);
        soundsSlider.setShowTickMarks(false); soundsSlider.setShowTickLabels(false);
        soundsSlider.setMajorTickUnit(0.25f);
        soundsSlider.setBlockIncrement(0.5f);
        soundsSlider.setValueChanging(true);
        soundsSlider.setOnMouseDragged(e -> {
            modifySounds.getChildren().remove(imageSoundOther);
            chooseIconSound(soundsSlider.getValue());
        });

        // Button to save
        Button saveButton = new ButtonLevel("SAVE",320,485);
        saveButton.setOnAction(e -> saveSoundProperties(slider.getValue(),soundsSlider.getValue()));
        saveButton.setPrefSize(120,50);
        saveButton.setStyle(getStyle() + "-fx-background-image: url('"+ Game.class.getResource("icons/button.png")+"'); -fx-opacity: 0.0");

        // Adding all the elements
        modifySounds.getChildren().addAll(backgroundMusic,environnementSounds,slider,imageSound,soundsSlider,imageSoundOther,saveButton);
        paneOptionsMenu.getChildren().addAll(modifySounds);
    }

    private void settingNamePlayer() {
        switch (indexSkin) {
            case 0 -> {model.player1.setName("Red Knight");}
            case 1 -> {model.player1.setName("Platine Knight");}
            case 2 -> {model.player1.setName("Gold Knight");}
            case 3 -> {model.player1.setName("Grey Knight");}
        }
        model.player1.chooseSkin();
    }

    private void chooseIconSound(double value) {
        // Changing if the volume is set at 0%
        if (value == 0){
            imageSound = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/icons/mute.png"));
            imageSound.setFitHeight(imageSound.getImage().getHeight()*0.20);
            imageSound.setFitWidth(imageSound.getImage().getWidth()*0.20);
        } else {
            imageSound = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/icons/sound.png"));
            imageSound.setFitHeight(imageSound.getImage().getHeight()*0.15);
            imageSound.setFitWidth(imageSound.getImage().getWidth()*0.15);
        }
        imageSound.setLayoutX(100); imageSound.setLayoutY(125);
        modifySounds.getChildren().add(imageSound);
    }

    private void saveSoundProperties(double valueMusic, double valueSounds) {
        // MUSIC
        Sounds.mediaPlayerBackgroundMusic.setVolume(valueMusic/100);
        Sounds.mediaPlayerCredit.setVolume(valueMusic/100);

        // SOUNDS
        Sounds.mediaPlayerHurt.setVolume(valueSounds/100);
    }
}
