package mszzsm.floppybird;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mszzsm.floppybird.game.FloppyBird;

import java.io.IOException;
//Egy segéd osztály ami segít a kód átláthatóságán. .fxml-ek közötti váltást segíti elő
public class fxmlLoader {
    public void fxmlLoaderSimple(String path, Scene scene, Stage stage)
    {
        FXMLLoader loader = new FXMLLoader(FloppyBird.class.getResource(path));
        Parent root = null;
        try
        {
            root = loader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void fxmlLoaderComplex(String path, Scene scene, Stage stage, ActionEvent event){
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
        stage.setScene(scene);
        stage.show();
    }
}
