package mszzsm.floppybird;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mszzsm.floppybird.FloppyBird;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;
    private Scene scene;

    public void switchToSelect(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(FloppyBird.class.getResource("/mszzsm/floppybird/Select.fxml"));
        Parent root = null;
        try
        {
            root = loader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void exit(){
        System.exit(0);
    }
}
