package jfx.mvn;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Controller_Dashboard {
    URL imageUrl_1En, imageUrl_1Ex;

    public Controller_Dashboard() {
        imageUrl_1En = getClass().getResource("/jfx/mvn/img/Endow.png");
        imageUrl_1Ex = getClass().getResource("/jfx/mvn/img/Endow@4x.png");
    }

    @FXML
    private Label MenuLabel1;

    @FXML
    private HBox Menu_1;

    @FXML
    private ImageView icon1_imgview;

    private void resetMenuStyles() {
        // Reset styles for all menu items
        Menu_1.setStyle("-fx-background-color: transparent");
        // Menu_2.setStyle("-fx-background-color: transparent");
        // Reset text color for all labels
        MenuLabel1.setTextFill(Color.web("#000000"));
        // MenuLabel2.setTextFill(Color.web("#000000"));
        // Reset images for all ImageViews
        icon1_imgview.setImage(new Image(imageUrl_1Ex.toString()));
        // icon2_imgview.setImage(new Image(imageUrl_2Ex.toString()));
    }

    @FXML
    void Menu_1Enter(MouseEvent event) {
        icon1_imgview.setImage(new Image(imageUrl_1En.toString()));
    }

    @FXML
    void Menu_1Exit(MouseEvent event) {
        icon1_imgview.setImage(new Image(imageUrl_1Ex.toString()));
    }

    @FXML
    void Menu_1Click(MouseEvent event) {
        Menu_1.setStyle("-fx-background-color: #264653");
        MenuLabel1.setStyle("-fx-text-fill: #FFFFFF;");
        icon1_imgview.setImage(new Image(imageUrl_1En.toString()));
    }
}
