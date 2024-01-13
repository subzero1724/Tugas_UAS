package jfx.mvn.admin;

import jfx.mvn.data;
import jfx.mvn.productData;
import jfx.mvn.KonektorSQL;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jfx.mvn.admin.Inventory_Control;

public class UpdatePane_Control implements Initializable {
    String path = "";
    Integer id = 0;

    private PreparedStatement prepare;
    private Statement stat;
    private Connection con;
    private ResultSet rs;

    private Alert alert;
    public Image image;

    @FXML
    private AnchorPane Add_Pane;

    @FXML
    private Button Import_Button;

    @FXML
    private ImageView Inv_Img;

    @FXML
    private TextField Product_Id;

    @FXML
    private TextField Product_Price;

    @FXML
    private TextField Product_Stocks;

    @FXML
    private ComboBox<String> Product_Type;

    @FXML
    private TextField Product_name;

    @FXML
    private Button Update_Button;

    public String[] typeList = { "Foods", "Drinks", "Electrics" };

    public void inventoryTypeList() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        Product_Type.setItems(listData);
    }

    @FXML
    void Import_Clicks(ActionEvent event) {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(Add_Pane.getScene().getWindow());

        if (file != null) {
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);

            Inv_Img.setImage(image);
        }
    }

    @FXML
    void Isi_ProdID(ActionEvent event) {
        String sql = "SELECT * FROM product WHERE prod_id = '" + Product_Id.getText() + "'";

        con = KonektorSQL.connectDB();
        try {
            stat = con.createStatement();
            rs = stat.executeQuery(sql);

            if (rs.next()) {
                String prodName = rs.getString("prod_name");
                String prodType = rs.getString("type");
                String prodStock = rs.getString("stock");
                String prodPrice = rs.getString("price");
                String prodIMG = rs.getString("image");
                Integer prodId = rs.getInt("id");

                Product_name.setText(prodName);
                Product_Type.getSelectionModel().select(prodType);
                Product_Stocks.setText(prodStock);
                Product_Price.setText(prodPrice);
                path = prodIMG;
                id = prodId;

                Image image = new Image("File:" + prodIMG);
                Inv_Img.setImage(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(path);
        System.out.println(Product_Id.getText());
        System.out.println(id);
    }

    @FXML
    void Update_Clicks(ActionEvent event) {
        if (Product_Id.getText().isEmpty()
                || Product_name.getText().isEmpty()
                || Product_Type.getSelectionModel().getSelectedItem() == null
                || Product_Stocks.getText().isEmpty()
                || path.isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }

        else {
            path = path.replace("\\", "\\\\");
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            String UpdateData = "UPDATE product SET "
                    + "prod_id = '" + (String) Product_Id.getText() + "', prod_name ='"
                    + Product_name.getText() + "', type = '"
                    + Product_Type.getSelectionModel().getSelectedItem() + "', stock= '"
                    + Product_Stocks.getText() + "', price = '"
                    + Product_Price.getText() + "', status = 'Ready', image = '"
                    + path + "', date = '" + String.valueOf(sqlDate) + "' WHERE id = " + id;

            con = KonektorSQL.connectDB();

            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE PRoduct ID: " + Product_Id.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = con.prepareStatement(UpdateData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
