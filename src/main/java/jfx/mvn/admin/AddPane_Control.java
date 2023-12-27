package jfx.mvn.admin;

import jfx.mvn.data;
import jfx.mvn.KonektorSQL;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddPane_Control implements Initializable {

    private Statement stat;
    private Connection con;
    private ResultSet rs;

    private Alert alert;

    @FXML
    private Button Add_Button;

    @FXML
    private TextField Product_Id;

    @FXML
    private TextField Product_Price;

    @FXML
    private TextField Product_Stocks;

    @FXML
    private ComboBox<?> Product_Type;

    @FXML
    private TextField Product_name;

    @FXML
    private Button Update_Button;

    private String[] typeList = { "Foods", "Drinks", "Electrics" };

    public void inventoryTypeList() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        Product_Type.setItems(listData);
    }

    private void generateIdBasedOnType(String type) {
        // Call the id_auto method to generate the ID based on the selected type
        id_auto(type);
    }

    public void id_auto(String type) {
        try {
            con = KonektorSQL.connectDB();
            String sql = "SELECT MAX(CAST(SUBSTRING(prod_id, LOCATE('-', prod_id) + 1) AS SIGNED)) AS no FROM product WHERE prod_id LIKE ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, type + "-%");
            rs = preparedStatement.executeQuery();

            int set_prod_id = 1;

            if (rs.next()) {
                set_prod_id = rs.getInt("no") + 1;
            }

            String no = String.format("%03d", set_prod_id);
            Product_Id.setText(type + "-" + no);

        } catch (SQLException ex) {
            Logger.getLogger(AddPane_Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Add_Clicks(ActionEvent event) {
        if (Product_Id.getText().isEmpty()
                || Product_name.getText().isEmpty()
                || Product_Type.getSelectionModel().getSelectedItem() == null
                || Product_Stocks.getText().isEmpty()
        // || data.path == null
        ) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            // Generate ID based on the selected item in Product_Type
            String type = "";
            if ("Foods".equals(Product_Type.getSelectionModel().getSelectedItem())) {
                type = "FDS";
            } else if ("Drinks".equals(Product_Type.getSelectionModel().getSelectedItem())) {
                type = "WTR";
            } else {
                type = "ELCT";
            }
            id_auto(type);
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '" + Product_Id.getText() + "'";
            con = KonektorSQL.connectDB();
            try {
                stat = con.createStatement();
                rs = stat.executeQuery(checkProdID);
                // String path = data.path;
                // path = path.replace("\\", "\\\\");

                // Rest of the code...
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Update_Clicks(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryTypeList();
        Product_Type.setOnAction(event -> {
            String selectedType = (String) Product_Type.getSelectionModel().getSelectedItem();
            if (selectedType != null) {
                generateIdBasedOnType(selectedType);
            }
        });
    }

}
