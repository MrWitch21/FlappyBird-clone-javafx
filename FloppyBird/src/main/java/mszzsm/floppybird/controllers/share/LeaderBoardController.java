package mszzsm.floppybird.controllers.share;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import mszzsm.floppybird.fxmlLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable {
    private final fxmlLoader loader = new fxmlLoader();
    private Scene scene;
    private Stage stage;
    @FXML
    private TableView<users> table_users;
    @FXML
    private TableColumn <users, String> col_username;
    @FXML
    private TableColumn <users, Integer> col_score;
    @FXML
    private TableColumn<users, String> col_num;
    //kitölti a ranglistát az adatbázisban szereplő adatok alapján
    @Override
    public void initialize(URL url, ResourceBundle rb){
        MysqlConnect mysqlConnect = new MysqlConnect();
        mysqlConnect.viewData();

        col_num.setCellValueFactory(cellData -> {
            int rowNumber = table_users.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(rowNumber)+".");
        });
        col_username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        col_score.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());
        table_users.setItems(mysqlConnect.getUsersList());
        mysqlConnect.close();;

    }
    //visszalépéss a kezdőoldalra
    public void switchToMain(ActionEvent event)
    {
        String MainMenuPath = "/mszzsm/floppybird/MainMenu.fxml";
        loader.fxmlLoaderComplex(MainMenuPath, scene, stage, event);
    }
}
