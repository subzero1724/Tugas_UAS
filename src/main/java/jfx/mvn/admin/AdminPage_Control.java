package jfx.mvn.admin;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AdminPage_Control {

    @FXML
    private BorderPane Border_Pane;

    @FXML
    private HBox Inventory_Button;

    @FXML
    private HBox Income_Button;

    @FXML
    private AnchorPane Main_Page;

    @FXML
    void Inventory_Click(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/mvn/Inventory_Page.fxml"));
        AnchorPane view = loader.load();
        Border_Pane.setCenter(view);
    }

    @FXML
    void Income_Click(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/mvn/Inventory_Page.fxml"));
        AnchorPane view = loader.load();
        Border_Pane.setCenter(view);
    }

}
