package mszzsm.floppybird.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mszzsm.floppybird.fxmlLoader;

public class SelectMenuController {
    private fxmlLoader loader = new fxmlLoader();
    @FXML
    private ToggleGroup toggleGroup;
    private Stage stage;
    private Scene scene;
    @FXML
    private ToggleButton Normal, Multi, Bot;
    @FXML
    private ImageView Normal_ImgV, Multi_ImgV, Bot_ImgV;
    @FXML
    private void updateImage() {
        if (Normal.isSelected()) {
            Normal_ImgV.setImage(new Image("/normal_selected.png"));
            Multi_ImgV.setImage(new Image("/multi.png"));
            Bot_ImgV.setImage(new Image("/bot.png"));
        } else if (Multi.isSelected()) {
            Normal_ImgV.setImage(new Image("/normal.png"));
            Multi_ImgV.setImage(new Image("/multi_selected.png"));
            Bot_ImgV.setImage(new Image("/bot.png"));
        } else if (Bot.isSelected()) {
            Normal_ImgV.setImage(new Image("/normal.png"));
            Multi_ImgV.setImage(new Image("/multi.png"));
            Bot_ImgV.setImage(new Image("/bot_selected.png"));
        }else{
            Normal_ImgV.setImage(new Image("/normal.png"));
            Multi_ImgV.setImage(new Image("/multi.png"));
            Bot_ImgV.setImage(new Image("/bot.png"));
        }
    }
    public void playButtonClicked(ActionEvent event) {
        Toggle selectedToggle = toggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            ToggleButton selectedButton = (ToggleButton) selectedToggle;
            String selectedMode = selectedButton.getId();
            //System.out.println("Kiválasztott játékmód: " + selectedMode);
            String singlePlayerCustomization_Path = "/mszzsm/floppybird/Custom1.fxml";
            String multiPlayerCustomization_Path = "/mszzsm/floppybird/MultiplayerCustomization.fxml";
            String playerVsBotCustomization_Path = "/mszzsm/floppybird/BotCustomization.fxml";
            switch (selectedMode){
                case "Normal":
                    loader.fxmlLoaderComplex(singlePlayerCustomization_Path, scene, stage, event);
                    break;
                case "Multi":
                    loader.fxmlLoaderComplex(multiPlayerCustomization_Path, scene, stage, event);
                    break;
                case "Bot":
                    loader.fxmlLoaderComplex(playerVsBotCustomization_Path, scene, stage, event);
                    break;
            }
        } else {
            System.out.println("Nincs játékmód kiválasztva.");
        }
    }
    public void switchToMain(ActionEvent event){
        String main_Path = "/mszzsm/floppybird/MainMenu.fxml";
        loader.fxmlLoaderComplex(main_Path, scene, stage, event);
    }
}
