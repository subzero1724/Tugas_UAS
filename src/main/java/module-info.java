module jfx.mvn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens jfx.mvn to javafx.fxml;
    exports jfx.mvn;
}
