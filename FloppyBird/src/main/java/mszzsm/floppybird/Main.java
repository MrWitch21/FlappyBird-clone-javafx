package mszzsm.floppybird;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mszzsm.floppybird.game.FloppyBird;

import java.io.IOException;
//Main osztály itt indul el a program és elnavigál minket a kezdőképernyőre
public class Main extends Application {
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(FloppyBird.class.getResource("/mszzsm/floppybird/MainMenu.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Főmenü Scene létrehozása
        Scene scene = new Scene(root);
        stage.setResizable(false);
        // Főmenü Stage beállítása
        stage.setTitle("Flappy Bird");
        stage.getIcons().add(new Image("/icon.png"));
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
