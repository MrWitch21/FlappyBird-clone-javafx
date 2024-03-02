package mszzsm.floppybird.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import mszzsm.floppybird.controllers.customization.UserPreferences;
import mszzsm.floppybird.fxmlLoader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
//kezdő képernyőt reprezentáló vezérlő
public class MainMenuController implements Initializable {
    @FXML
    private Slider volumeSlider;
    public static int volume = 20;
    private final fxmlLoader loader = new fxmlLoader();
    private Stage stage;
    private Scene scene;
    private UserPreferences pref = new UserPreferences();
    private MediaPlayer test = new MediaPlayer(new Media(new File("src/main/resources/score.mp3").toURI().toString()));
    //beállítja illetve megváltosztatja a játék hangerejét
    public void initialize(URL url, ResourceBundle rb) {
        if (!pref.loadPreferences()) {volumeSlider.setValue(10);} else {volumeSlider.setValue(volume);}
        test.setVolume(volume/100.0);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            volume = newVal.intValue();
            test.setVolume(volume/100.0);
            test.stop();
            test.play();
        });
    }
    //Játékmód kiválasztásra navigáló
    public void switchToSelect(ActionEvent event) {
        String selectPath = "/mszzsm/floppybird/Select.fxml";
        pref.savePreferences();
        loader.fxmlLoaderComplex(selectPath, scene, stage, event);
    }
    //Raglistára navigáló
    public void switchToLB(ActionEvent event) {
        String leaderboardPath = "/mszzsm/floppybird/LeaderBoard.fxml";
        pref.savePreferences();
        loader.fxmlLoaderComplex(leaderboardPath, scene, stage, event);
    }
    //Kilépés a programból
    public void exit(){
        pref.savePreferences();
        System.exit(0);
    }
}
