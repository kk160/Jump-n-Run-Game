module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;

    opens org.example to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.example;
    exports org.example.sprites;
    exports org.example.exceptions;
    opens org.example.sprites to com.fasterxml.jackson.databind, javafx.fxml;
}
