package jfx.mvn;

import javafx.scene.image.Image;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Controller_Dashboard {
    URL imageUrl_1, imageUrl_1Ex;

    public Controller_Dashboard() {
        imageUrl_1 = getClass().getResource("/jfx/mvn/img/Endow.png");
        imageUrl_1Ex = getClass().getResource("/jfx/mvn/img/Endow@4x.png");
    }

    @FXML
    private HBox icon1;

    @FXML
    private ImageView icon1_imgview;

    @FXML
    void icon1_enter(MouseEvent event) {
        icon1_imgview.setImage(new Image(imageUrl_1.toString()));
    }

    @FXML
    void icon1_exit(MouseEvent event) {
        icon1_imgview.setImage(new Image(imageUrl_1Ex.toString()));
    }

    @FXML
    void icon1_click(MouseEvent event) {

    }
}
