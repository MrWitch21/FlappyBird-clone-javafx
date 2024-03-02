package mszzsm.floppybird;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;

public class SelectMenuController {
    private final String Main_Path = "/mszzsm/floppybird/MainMenu.fxml";
    private final String SinglePlayerCustomization_Path ="/mszzsm/floppybird/Custom1.fxml";
    private final String MultiPlayerCustomization_Path = "/mszzsm/floppybird/MultiplayerCustomization.fxml";
    private final String PlayerVsBotCustomization_Path = "/mszzsm/floppybird/BotCustomization.fxml";
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
            System.out.println("Kiválasztott játékmód: " + selectedMode);
            switch (selectedMode){
                case "Normal":
                    switchScene(event, SinglePlayerCustomization_Path);
                    break;
                case "Multi":
                    switchScene(event,MultiPlayerCustomization_Path);
                    break;
                case "Bot":
                    switchScene(event,PlayerVsBotCustomization_Path);
                    break;
            }
        } else {
            System.out.println("Nincs játékmód kiválasztva.");
        }
    }
    public void switchScene(ActionEvent event, String path)
    {
        FXMLLoader loader = new FXMLLoader(FloppyBird.class.getResource(path));
        Parent root = null;
        try
        {
            root = loader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        //Kezdő beállítások
        stage.setScene(scene);
        stage.show();
    }
    public void switchToMain(ActionEvent event){
        switchScene(event, Main_Path);
    }
}
