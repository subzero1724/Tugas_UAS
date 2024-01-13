package jfx.mvn.Voucher;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

public class VoucherCard_Control {
    private Alert alert;

    @FXML
    private TextArea Deskripsi_Voucher;

    @FXML
    private Label Voucher_Label;

    @FXML
    void Copy_Button(MouseEvent event) {
        ClipboardContent content = new ClipboardContent();
        content.putString(Voucher_Label.getText());
        Clipboard.getSystemClipboard().setContent(content);

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Copy!");
        alert.showAndWait();
    }

}
