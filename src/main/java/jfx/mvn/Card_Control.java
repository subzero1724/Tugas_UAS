package jfx.mvn;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Card_Control implements Initializable {

    @FXML
    private Label Stock_Label;

    @FXML
    private Button Btn_Keranjang;

    @FXML
    private Label Harga_Barang;

    @FXML
    private ImageView Image_Barang;

    @FXML
    private Label Nama_Barang;

    @FXML
    private Spinner<Integer> Prod_Spinner;

    @FXML
    private VBox card_form;

    private productData prodData;
    private Image image;

    private String prodID;
    private String type;
    private String prod_date;
    private String prod_image;

    private SpinnerValueFactory<Integer> spin;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    public Card_Control() {

    }

    public void setData(productData prodData) {
        this.prodData = prodData;

        prod_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
        type = prodData.getType();
        prodID = prodData.getProductId();
        Nama_Barang.setText(prodData.getProductName());
        Harga_Barang.setText("Rp." + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 153, 160, false, true);
        Image_Barang.getStyleClass().add("rounded-image");
        Image_Barang.setImage(image);
        Stock_Label.setText("Stock Available : " + String.valueOf(prodData.getStock()));
        pr = prodData.getPrice();

    }

    private int qty;

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        Prod_Spinner.setValueFactory(spin);
    }

    private double totalP;
    private double pr;

    public void AddKeranjang() {

        qty = Prod_Spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE prod_id = '"
                + prodID + "'";

        connect = KonektorSQL.connectDB();

        try {
            int checkStck = 0;
            String checkStock = "SELECT stock FROM product WHERE prod_id = '"
                    + prodID + "'";

            prepare = connect.prepareStatement(checkStock);
            result = prepare.executeQuery();

            if (result.next()) {
                checkStck = result.getInt("stock");
            }

            if (checkStck == 0) {

                String updateStock = "UPDATE product SET prod_name ='"
                        + Nama_Barang.getText() + "', type = '"
                        + type + "', stock = 0, price = '"
                        + pr + "', status = 'Out Of Stocks', image = '"
                        + prod_image + "', date = '" + prod_date + "' WHERE prod_id = '"
                        + prodID + "' ";
            }
            prepare = connect.prepareStatement(checkAvailable);
            result = prepare.executeQuery();

            if (result.next()) {
                check = result.getString("status");
            }

            if (!check.equals("Ready") || qty == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong :3");
                alert.showAndWait();
            } else {
                prod_image = prod_image.replace("\\", "\\\\");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuantity();
    }

}
