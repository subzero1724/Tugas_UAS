package jfx.mvn;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.event.*;
import javafx.fxml.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller_Dashboard implements Initializable {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    URL imageUrl_1En, imageUrl_1Ex, imageUrl_2En, imageUrl_2Ex;
    private AtomicBoolean Menu1_clicked = new AtomicBoolean(false);
    private AtomicBoolean Menu2_clicked = new AtomicBoolean(false);
    private AtomicBoolean Menu3_clicked = new AtomicBoolean(false);

    public Controller_Dashboard() {
        imageUrl_1En = getClass().getResource("/jfx/mvn/img/box_black.png");
        imageUrl_1Ex = getClass().getResource("/jfx/mvn/img/box_white.png");

        imageUrl_2En = getClass().getResource("/jfx/mvn/img/Endow@4x.png");
        imageUrl_2Ex = getClass().getResource("/jfx/mvn/img/Endow.png");
    }

    @FXML
    private BorderPane Border_Pane;

    @FXML
    private AnchorPane Main_Page;

    @FXML
    private Label MenuLabel1;

    @FXML
    private Label MenuLabel2;

    @FXML
    private HBox Menu_1;

    @FXML
    private HBox Menu_2;

    @FXML
    private HBox Menu_3;

    @FXML
    private ImageView icon1_imgview;

    @FXML
    private ImageView icon2_imgview;

    public void getCustomerId(String username, String password) {
        String customerId = "Kosong";
        try {
            connect = KonektorSQL.connectDB();
            String sql = "SELECT customer_id FROM register_user WHERE Username = ? AND Password = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            result = preparedStatement.executeQuery();

            if (result.next()) {
                customerId = result.getString("customer_id");
            }

            data.cID = customerId;

        } catch (SQLException ex) {
            Logger.getLogger(signup_control.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setMenuStyles(HBox menu, Label label) {
        menu.setStyle("-fx-background-color: #F5D7DB");
        label.setStyle("-fx-text-fill: #1B3358");
    }

    private void resetMenuStyles() {
        Menu1_clicked.set(false);
        Menu2_clicked.set(false);

        Menu_1.setStyle("-fx-background-color: #1B3358");
        MenuLabel1.setStyle("-fx-text-fill: #FFFFFF");
        icon1_imgview.setImage(new Image(imageUrl_1Ex.toString()));

        Menu_2.setStyle("-fx-background-color: #1B3358");
        MenuLabel2.setStyle("-fx-text-fill: #FFFFFF");
        icon2_imgview.setImage(new Image(imageUrl_2Ex.toString()));
    }

    // private void handleMenuClick(HBox menu, AtomicBoolean clicked, Label label,
    // ImageView icon) throws IOException {
    // resetMenuStyles();
    // clicked.set(true);
    // menu.setStyle("-fx-background-color: #264653");
    // label.setStyle("-fx-text-fill: #FFFFFF;");
    // icon.setImage(new Image(imageUrl_1En.toString()));
    // VBox view = FXMLLoader.load(getClass().getResource("primary.fxml"));
    // Border_Pane.setCenter(view);
    // }

    @FXML
    void Menu_1Click(MouseEvent event) throws IOException {
        if (!Menu1_clicked.get()) {
            resetMenuStyles();
            setMenuStyles(Menu_1, MenuLabel1);
            icon1_imgview.setImage(new Image(imageUrl_1En.toString()));

            AnchorPane view = FXMLLoader.load(getClass().getResource("Product_Page.fxml"));
            Border_Pane.setCenter(view);
            // System.out.println(getCustomerId(data.username, data.password));

            Menu1_clicked.set(true);
        }
    }

    @FXML
    void Menu_2Click(MouseEvent event) throws IOException {
        if (!Menu2_clicked.get()) {
            resetMenuStyles();
            setMenuStyles(Menu_2, MenuLabel2);
            icon2_imgview.setImage(new Image(imageUrl_2En.toString()));
            AnchorPane view = FXMLLoader.load(getClass().getResource("VoucherPage.fxml"));
            Border_Pane.setCenter(view);

            Menu2_clicked.set(true);
        }
    }

    @FXML
    void Menu_3Click(MouseEvent event) throws IOException {
        if (!Menu3_clicked.get()) {
            AnchorPane view = FXMLLoader.load(getClass().getResource("Keranjang_Page.fxml"));
            Border_Pane.setCenter(view);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
