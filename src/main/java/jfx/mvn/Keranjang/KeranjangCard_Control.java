package jfx.mvn.Keranjang;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import jfx.mvn.KonektorSQL;
import jfx.mvn.productData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class KeranjangCard_Control implements Initializable {
    private productData prodData;
    private String prodID;
    private String type;
    private String prod_date;
    private String prod_image;
    private int ID;
    private Image image;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Label Keranjang_ID;

    @FXML
    private ImageView KeranjangIMG_View;

    @FXML
    private Label Keranjang_ProductID;

    @FXML
    private Label Keranjang_ProductName;

    @FXML
    private Label Keranjang_ProductQTY;

    @FXML
    private Label Keranjang_ProductType;

    @FXML
    private Label Keranjang_TotalPrice;

    public void setData(productData prodData) {
        this.prodData = prodData;

        prod_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
        type = prodData.getType();
        prodID = prodData.getProductId();
        ID = prodData.getId();

        Keranjang_ProductName.setText(prodData.getProductName());
        Keranjang_TotalPrice.setText("Rp. " + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 153, 160, false, true);
        KeranjangIMG_View.setImage(image);
        Keranjang_ProductQTY.setText("X" + String.valueOf(prodData.getQuantity()));
        Keranjang_ProductID.setText(prodID);
        Keranjang_ProductType.setText(type);
        Keranjang_ID.setText(ID + "");
    }

    @FXML
    void Delete_Btn(MouseEvent event) {
        ID = prodData.getId();
        prodID = prodData.getProductId();
        int quantityToRemove = prodData.getQuantity(); // Get the quantity from the product data

        String getStockQuery = "SELECT stock FROM product WHERE prod_id = ?";
        String updateStockQuery = "UPDATE product SET stock = ? WHERE prod_id = ?";
        String deleteCartQuery = "DELETE FROM customer WHERE id = ?"; // Assuming 'customer' is your cart table

        connect = KonektorSQL.connectDB();
        try {
            connect.setAutoCommit(false); // Start a transaction

            // Retrieve current stock
            prepare = connect.prepareStatement(getStockQuery);
            prepare.setString(1, prodID);
            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                int currentStock = rs.getInt("stock");
                int newStock = currentStock + quantityToRemove;

                // Update stock
                prepare = connect.prepareStatement(updateStockQuery);
                prepare.setInt(1, newStock);
                prepare.setString(2, prodID);
                prepare.executeUpdate();

                // Delete item from cart
                prepare = connect.prepareStatement(deleteCartQuery);
                prepare.setInt(1, ID);
                prepare.executeUpdate();

                connect.commit(); // Commit transaction

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Item successfully deleted and stock updated!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            try {
                connect.rollback(); // Rollback in case of error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null)
                    prepare.close();
                if (connect != null)
                    connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
