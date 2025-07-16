module peds_tentativa_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.jdi;
    requires java.sql;
    requires java.compiler;
    requires java.desktop;


    exports App;
    exports Controller;
    exports Model;
    exports View;
    requires com.google.gson;
    opens Model to com.google.gson;

    opens Controller to javafx.fxml;
    opens View to javafx.fxml;
}
