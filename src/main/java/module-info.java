module jfx.mvn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens jfx.mvn to javafx.fxml;
    opens jfx.mvn.admin to javafx.fxml;
    opens jfx.mvn.Keranjang to javafx.fxml;

    exports jfx.mvn;
    exports jfx.mvn.admin;
    exports jfx.mvn.Keranjang;
}
