package jfx.mvn.Keranjang;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
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
    private Image image;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

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

        Keranjang_ProductName.setText(prodData.getProductName());
        Keranjang_TotalPrice.setText("Rp. " + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 153, 160, false, true);
        KeranjangIMG_View.setImage(image);
        Keranjang_ProductQTY.setText("X" + String.valueOf(prodData.getQuantity()));
        Keranjang_ProductID.setText(prodID);
        Keranjang_ProductType.setText(type);
    }

    @FXML
    void Delete_Btn(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
