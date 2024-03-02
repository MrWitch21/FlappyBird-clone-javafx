module mszzsm.floppybird {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires javafx.media;
    requires encog.core;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

    opens mszzsm.floppybird to javafx.fxml;
    exports mszzsm.floppybird.game;
    opens mszzsm.floppybird.game to javafx.fxml;
    opens mszzsm.floppybird.game.bot to javafx.fxml;
    exports mszzsm.floppybird.game.bot;
}