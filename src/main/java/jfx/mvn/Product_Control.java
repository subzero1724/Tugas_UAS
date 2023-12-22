package jfx.mvn;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Product_Control implements Initializable {
    Connection con;
    PreparedStatement prepare;
    Statement stat;
    ResultSet rs;

    public Product_Control() {
        // KonektorSQL DB = new KonektorSQL();
        // DB.connect();
        // con = DB.con;
        // stat = DB.stm;

    }

    @FXML
    private GridPane Menu_Grid;

    @FXML
    private AnchorPane Prod_Anchor;

    @FXML
    private ScrollPane Prod_Scroll;

    public void CardDisplay() {
        int row = 0;
        int collumn = 0;

        Menu_Grid.getChildren().clear();
        Menu_Grid.getRowConstraints().clear();
        Menu_Grid.getColumnConstraints().clear();
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("card.fxml"));
            VBox pane = load.load();
            Card_Control card = load.getController();

            if (collumn == 3) {
                collumn = 0;
                row += 1;
            }

            Menu_Grid.add(pane, collumn++, row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CardDisplay();
    }

}