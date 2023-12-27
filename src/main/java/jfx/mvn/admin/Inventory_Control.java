package jfx.mvn.admin;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Inventory_Control {

    @FXML
    private Button Add_Button;

    @FXML
    private Button Edit_Button;

    @FXML
    void Add_Click(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/mvn/AddPane.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Add Item"); // Title for the popup
            stage.setScene(scene);

            // Set the modality, so it blocks events to other windows
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the stage
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    void Delete_Click(ActionEvent event) {

    }

    @FXML
    void Edit_Click(ActionEvent event) {

    }

}
