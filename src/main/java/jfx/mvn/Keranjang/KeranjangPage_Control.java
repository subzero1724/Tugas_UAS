package jfx.mvn.Keranjang;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfx.mvn.Card_Control;
import jfx.mvn.Controller_Dashboard;
import jfx.mvn.productData;
import jfx.mvn.KonektorSQL;
import jfx.mvn.data;

public class KeranjangPage_Control implements Initializable {

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Alert alert;

    productData prodData;

    private ObservableList<productData> KeranjangCardListData = FXCollections.observableArrayList();

    @FXML
    private Button Buy_Button;

    @FXML
    private GridPane Keranjang_Grid;

    @FXML
    private Label Total_Label;

    public double totalp;

    private int getId;

    public void DisplayTotal() {
        menuGetTotal();
        Total_Label.setText("Rp." + totalp);
    }

    public void menuGetTotal() {

        Controller_Dashboard cDashboard = new Controller_Dashboard();
        cDashboard.getCustomerId(data.username, data.password);

        String total = "SELECT SUM(price) FROM customer WHERE customer_id = ?";
        connect = KonektorSQL.connectDB();
        try {
            prepare = connect.prepareStatement(total);
            prepare.setString(1, data.cID); // Set the customer_id parameter
            result = prepare.executeQuery();

            if (result.next()) {
                totalp = result.getDouble("SUM(price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<productData> MenuGetOrder() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        Controller_Dashboard cDashboard = new Controller_Dashboard();
        cDashboard.getCustomerId(data.username, data.password);

        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        connect = KonektorSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, data.cID); // Set the customer_id parameter
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void Items_Delete() {
        // Controller_Dashboard cDashboard = new Controller_Dashboard();
        // cDashboard.getCustomerId(data.username, data.password);

        String sql = "DELETE * FROM customer WHERE id = ?";
        connect = KonektorSQL.connectDB();

        try {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this order?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, prodData.getId()); // Set the customer_id parameter
                result = prepare.executeQuery();

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("successfully Deleted!");
                alert.showAndWait();

                // TO UPDATE YOUR TABLE VIEW
                KeranjangCardDisplay();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void KeranjangCardDisplay() {

        KeranjangCardListData.clear();
        KeranjangCardListData.addAll(MenuGetOrder());

        int row = 0;
        int collumn = 0;

        Keranjang_Grid.getChildren().clear();
        Keranjang_Grid.getRowConstraints().clear();
        Keranjang_Grid.getColumnConstraints().clear();

        for (int i = 0; i < KeranjangCardListData.size(); i++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/jfx/mvn/CardKeranjang.fxml"));
                AnchorPane pane = load.load();
                KeranjangCard_Control card = load.getController();
                card.setData(KeranjangCardListData.get(i));

                if (collumn == 1) {
                    collumn = 0;
                    row += 1;
                }

                Keranjang_Grid.add(pane, collumn++, row);
                GridPane.setMargin(pane, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Buy_Clicks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/mvn/payment_popUp.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Payment"); // Title for the popup
            stage.setScene(scene);

            // Set the modality, so it blocks events to other windows
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            // Show the stage
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        KeranjangCardDisplay();
        DisplayTotal();

    }
}
