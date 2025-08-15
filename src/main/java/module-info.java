module game.plantsvszambies {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    //requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires com.almasb.fxgl.core;
    requires java.desktop;

    opens game.plantsvszambies to javafx.fxml;
    exports game.plantsvszambies;
}