package mszzsm.floppybird.controllers.customization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mszzsm.floppybird.fxmlLoader;
import mszzsm.floppybird.game.*;

import java.net.URL;
import java.util.ResourceBundle;
//Az osztály felelős a kinézetek megjelenítésére hogy kiválassza a felhasználó, hogy hogyan szeretne játszani
public class CustomizationMenuController implements Initializable {
    private final fxmlLoader loader = new fxmlLoader();
    protected Stage stage;
    protected Scene scene;
    @FXML
    protected ImageView player1_bird;
    @FXML
    protected ImageView pipes;
    @FXML
    protected ImageView background;
    protected final Customization imagies = new Customization();
    //átadja a kinézetet a játékvezérlőnek és elindítja a játékot normal vagy bot módban
    public void startGame(ActionEvent event) {
        if(imagies.saveIndexes()){
            //System.out.println("Sikeres mentés");
        }else{
            //System.out.println("Sikertelen mentés");
        }
        FloppyBird game = new FloppyBird(imagies.getBirdAsset(), imagies.getPipeAsset(), imagies.getBgAsset(), "Normal");
        game.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
    public void startGameBot(ActionEvent event){
        if(imagies.saveIndexes()){
            System.out.println("Sikeres mentés");
        }else{
            System.out.println("Sikertelen mentés");
        }
        FloppyBird game = new FloppyBird(imagies.getBirdAsset(), imagies.getPipeAsset(), imagies.getBgAsset(), "Bot");
        game.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
    //Visszalép a módkiválasztó oldalra
    public void switchToSelect(ActionEvent event) {
        String SelectPath = "/mszzsm/floppybird/Select.fxml";
        loader.fxmlLoaderComplex(SelectPath, scene, stage, event);
    }
    //vált a kinézetek között hogy kitudja választani a felhasználó a számára a megfelelő kinézetet
    public void arrowUp(ActionEvent event) {
        String clicked = ((Button) event.getSource()).getId();
        handleArrowUpAction(clicked);
    }

    public void arrowDown(ActionEvent event) {
        String clicked = ((Button) event.getSource()).getId();
        handleArrowDownAction(clicked);
    }

    protected void handleArrowUpAction(String clicked) {
        if ("bird_u".equals(clicked)) {
            player1_bird.setImage(imagies.getNextBirdImage());
            centerImage(player1_bird);
        } else if ("pipe_u".equals(clicked)) {
            pipes.setImage(imagies.getNextPipeImage());
            centerImage(pipes);
        } else if ("bg_u".equals(clicked)) {
            background.setImage(imagies.getNextBgImage());
            centerImage(background);
        }
    }

    protected void handleArrowDownAction(String clicked) {
        if ("bird_d".equals(clicked)) {
            player1_bird.setImage(imagies.getPrevBirdImage());
            centerImage(player1_bird);
        } else if ("pipe_d".equals(clicked)) {
            pipes.setImage(imagies.getPrevPipeImage());
            centerImage(pipes);
        } else if ("bg_d".equals(clicked)) {
            background.setImage(imagies.getPrevBgImage());
            centerImage(background);
        }
    }
    //középre igazítja keretben a képet
    protected void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = Math.min(ratioX, ratioY);

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);
        }
    }
    //betölti a korábban kiválasztot kinézetet vagy az alapkinézeteket
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image[] startingImg = imagies.setup();
        player1_bird.setImage(startingImg[0]);
        centerImage(player1_bird);
        pipes.setImage(startingImg[2]);
        background.setImage(startingImg[3]);
    }
}
