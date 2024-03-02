module mszzsm.floppybird {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires javafx.media;
    requires java.prefs;
    requires java.sql;

    opens mszzsm.floppybird to javafx.fxml;
    exports mszzsm.floppybird;
    exports mszzsm.floppybird.game;
    opens mszzsm.floppybird.game to javafx.fxml;
    exports mszzsm.floppybird.game.objects;
    opens mszzsm.floppybird.game.objects to javafx.fxml;
    exports mszzsm.floppybird.controllers;
    opens mszzsm.floppybird.controllers to javafx.fxml;
    exports mszzsm.floppybird.controllers.share;
    opens mszzsm.floppybird.controllers.share to javafx.fxml;
    exports mszzsm.floppybird.controllers.customization;
    opens mszzsm.floppybird.controllers.customization to javafx.fxml;

}