package jfx.mvn.Keranjang;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUp_Payment_Control {

    @FXML
    private Label Close_btn;

    @FXML
    private HBox Non_Tunai;

    @FXML
    private AnchorPane PopUp_Payment;

    @FXML
    private HBox Tunai;

    @FXML
    void Close_Click(MouseEvent event) {
        // Dapatkan Stage dari label
        Stage stage = (Stage) Close_btn.getScene().getWindow();
        // Tutup stage
        stage.close();
    }

    @FXML
    void NonTunai_Click(MouseEvent event) {

    }

    @FXML
    void Tunai_Click(MouseEvent event) {
        Stage close = (Stage) Tunai.getScene().getWindow();
        close.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/mvn/Tunai_Payment.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Pembayaran Tunai"); // Title for the popup
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
