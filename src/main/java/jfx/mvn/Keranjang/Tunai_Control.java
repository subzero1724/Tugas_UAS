package jfx.mvn.Keranjang;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jfx.mvn.Controller_Dashboard;
import jfx.mvn.KonektorSQL;
import jfx.mvn.productData;
import jfx.mvn.data;

public class Tunai_Control implements Initializable {
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;

    private ObservableList<productData> TunaiCardListData = FXCollections.observableArrayList();

    @FXML
    private GridPane GridPage;

    @FXML
    private TextField Input_Field;

    @FXML
    private TextField Kembalian_Field;

    @FXML
    private Label Potongan_harga_Label;

    @FXML
    private Label Total_Harga_Label;

    @FXML
    private Label Total_Items_Label;

    @FXML
    private Label Total_Label;

    @FXML
    private TextField Voucher_Field;

    @FXML
    private Button btn_beli;

    public int QTY;
    public int discount = 0;
    public double total;

    public void DisplayDisc() {
        Potongan_harga_Label.setText(discount + "%");
    }

    public void DisplayFinalTotal() {
        KeranjangPage_Control kPageCTRL = new KeranjangPage_Control();
        kPageCTRL.menuGetTotal();
        if (discount == 0) {
            total = (double) kPageCTRL.totalp;
            Total_Label.setText("Rp." + kPageCTRL.totalp);
        } else {
            total = kPageCTRL.totalp * (discount / 100);
            Total_Label.setText("Rp." + total);
        }
    }

    public void GetTotalQTY() {
        String sql = "SELECT SUM(quantity) FROM customer WHERE customer_id = ?";
        connect = KonektorSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, data.cID); // Set the customer_id parameter
            result = prepare.executeQuery();

            if (result.next()) {
                QTY = result.getInt("SUM(quantity)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DisplayTotalQTY() {
        GetTotalQTY();
        Total_Items_Label.setText(QTY + " X");
    }

    public void DisplayTotal() {
        KeranjangPage_Control kPageCTRL = new KeranjangPage_Control();
        kPageCTRL.menuGetTotal();
        Total_Harga_Label.setText("Rp." + kPageCTRL.totalp);

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

    public void display() {
        TunaiCardListData.clear();
        TunaiCardListData.addAll(MenuGetOrder());

        int row = 0;
        int collumn = 0;

        GridPage.getChildren().clear();
        GridPage.getRowConstraints().clear();
        GridPage.getColumnConstraints().clear();

        for (int i = 0; i < TunaiCardListData.size(); i++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/jfx/mvn/Card_Tunai.fxml"));
                AnchorPane pane = load.load();
                CardTunai_Control card = load.getController();
                card.setData(TunaiCardListData.get(i));

                if (collumn == 1) {
                    collumn = 0;
                    row += 1;
                }

                GridPage.add(pane, collumn++, row);
                GridPane.setMargin(pane, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void BtnBeli_Action(ActionEvent event) {
        int getInput = Integer.parseInt(Input_Field.getText());
        double kembalian = getInput - total;
        if (kembalian < 0) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Uang anda Kurang Silahkan Coba lagi");
            alert.showAndWait();
            Input_Field.setText("");
        } else {
            Kembalian_Field.setText(String.valueOf(kembalian));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        display();
        DisplayTotal();
        DisplayTotalQTY();
        DisplayDisc();
        DisplayFinalTotal();
    }

}
