package jfx.mvn.Keranjang;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class KeranjangPage_Control implements Initializable {

    @FXML
    private Button Buy_Button;

    @FXML
    private GridPane Keranjang_Grid;

    @FXML
    private Label Total_Label;

    @FXML
    void Buy_Clicks(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
