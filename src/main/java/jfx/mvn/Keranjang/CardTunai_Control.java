package jfx.mvn.Keranjang;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jfx.mvn.productData;

public class CardTunai_Control implements Initializable {
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
    private Label ItemCode_Label;

    @FXML
    private Label ItemType_Label;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private Label QTY_Label;

    @FXML
    private Label TotalHarga_Label;

    @FXML
    private ImageView Img;

    public void setData(productData prodData) {
        this.prodData = prodData;

        prod_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
        type = prodData.getType();
        prodID = prodData.getProductId();

        ProductNameLabel.setText(prodData.getProductName());
        TotalHarga_Label.setText("Rp. " + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 153, 160, false, true);
        Img.setImage(image);
        QTY_Label.setText("X" + String.valueOf(prodData.getQuantity()));
        ItemCode_Label.setText(prodID);
        ItemType_Label.setText(type);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
