package mszzsm.floppybird.controllers.share;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mszzsm.floppybird.fxmlLoader;
import mszzsm.floppybird.game.FloppyBird;
//Teljesítmmény megosztására sszolgáló vezérlő
public class ShareController {
    private final fxmlLoader loader = new fxmlLoader();
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField input;
    @FXML
    private Label message;
    //Visszalépés a kezdőképernyőre
    public void switchToMain(ActionEvent event)
    {
        String MainMenuPath = "/mszzsm/floppybird/MainMenu.fxml";
        loader.fxmlLoaderComplex(MainMenuPath, scene, stage, event);
    }
    //Egy ellenőrzés követően lementi az adatokat az adatbázisba
    public boolean saveToDB(ActionEvent event)
    {
        String username = input.getText();
        int score = FloppyBird.sharescore;

        if(username == null || username.length()<2 || username.length()>128){
            message.setText("Please enter a valid name");
            return false;
        }

        MysqlConnect mysqlConnect = new MysqlConnect();
        if(mysqlConnect.insertUser(username,score)){
            switchToMain(event);
        }else {
            message.setText("Failed to save data: this username already exist in the database.");
            mysqlConnect.close();
            return false;
        }
        mysqlConnect.close();
        return true;
    }
}
