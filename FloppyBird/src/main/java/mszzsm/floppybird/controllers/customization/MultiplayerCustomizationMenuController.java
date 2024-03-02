package mszzsm.floppybird.controllers.customization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mszzsm.floppybird.game.FloppyBird;

import java.net.URL;
import java.util.ResourceBundle;

public class MultiplayerCustomizationMenuController extends CustomizationMenuController implements Initializable {
    @FXML
    private ImageView player2_bird;
    //beálítja a kinézetek megjelenítését
    public void initialize(URL url, ResourceBundle rb)
    {
        Image[] startingImg = imagies.setup();
        player1_bird.setImage(startingImg[0]);
        centerImage(player1_bird);
        player2_bird.setImage(startingImg[1]);
        centerImage(player2_bird);
        pipes.setImage(startingImg[2]);
        background.setImage(startingImg[3]);
    }
    //elindítja a játékot többjátékos módban
    @Override
    public void startGame(ActionEvent event){
        if(imagies.saveIndexes()){
            //System.out.println("Sikeres mentés");
        }else{
            //System.out.println("Sikertelen mentés");
        }
        FloppyBird game = new FloppyBird(imagies.getBirdAsset(), imagies.getBird2Asset(),imagies.getPipeAsset(),imagies.getBgAsset());
        game.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
    //vált a kinézetek között hogy kitudja választani a felhasználó a számára a megfelelő kinézetet
    @Override
    protected void handleArrowUpAction(String clicked) {
        super.handleArrowUpAction(clicked);

        if ("bird_u2".equals(clicked)) {
            player2_bird.setImage(imagies.getNextBird2Image());
            centerImage(player2_bird);
        }
    }

    @Override
    protected void handleArrowDownAction(String clicked) {
        super.handleArrowDownAction(clicked);

        if ("bird_d2".equals(clicked)) {
            player2_bird.setImage(imagies.getPrevBird2Image());
            centerImage(player2_bird);
        }
    }
}
