package mszzsm.floppybird;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MultiplayerCustomizationMenuController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private ImageView player1_bird;
    @FXML
    private ImageView player2_bird;
    @FXML
    private ImageView pipes;
    @FXML
    private ImageView background;
    private final Costumization imagies = new Costumization();

    public void startGame(ActionEvent event){
        if(imagies.saveIndexes()){
            System.out.println("Sikeres mentés");
        }else{
            System.out.println("Sikertelen mentés");
        }
        FloppyBird game = new FloppyBird(imagies.getBirdAsset(),imagies.getPipeAsset(),imagies.getBgAsset());
        game.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
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
    public  void arrowUp(ActionEvent event){
        String clicked = ((Button) event.getSource()).getId();
        switch (clicked){
            case "bird_u":
                player1_bird.setImage(imagies.getNextBirdImage());
                centerImage(player1_bird);
                break;
            case "bird_u2":
                player2_bird.setImage(imagies.getNextBird2Image());
                centerImage(player2_bird);
                break;
            case "pipe_u":
                pipes.setImage(imagies.getNextPipeImage());
                centerImage(pipes);
                break;
            case "bg_u":
                background.setImage(imagies.getNextBgImage());
                centerImage(background);
                break;
        }
    }
    public void arrowDown(ActionEvent event)
    {
        String clicked = ((Button) event.getSource()).getId();
        switch (clicked){
            case "bird_d":
                player1_bird.setImage(imagies.getPrevBirdImage());
                centerImage(player1_bird);
                break;
            case "bird_d2":
                player2_bird.setImage(imagies.getPrevBird2Image());
                centerImage(player2_bird);
                break;
            case "pipe_d":
                pipes.setImage(imagies.getPrevPipeImage());
                centerImage(pipes);
                break;
            case "bg_d":
                background.setImage(imagies.getPrevBgImage());
                centerImage(background);
                break;
        }
    }
    private void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }
}
