package jfx.mvn.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfx.mvn.KonektorSQL;
import jfx.mvn.productData;

public class Inventory_Control implements Initializable {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private TableView<productData> InvTabelView;

    @FXML
    private Button Add_Button;

    @FXML
    private TableColumn<productData, String> DateProd_Collumn;

    @FXML
    private Button Edit_Button;

    @FXML
    private TableColumn<productData, String> IdProd_Collumn;

    @FXML
    private TableColumn<productData, String> PriceProd_Collumn;

    @FXML
    private TableColumn<productData, String> ProdName_Collumn;

    @FXML
    private TableColumn<productData, String> StocksProd_Collumn;

    @FXML
    private TableColumn<productData, String> TypeProd_Collumn;

    public ObservableList<productData> inventoryDataList() {

        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = KonektorSQL.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prodData;

            while (result.next()) {

                prodData = new productData(
                        result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prodData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<productData> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        IdProd_Collumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        ProdName_Collumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        TypeProd_Collumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        StocksProd_Collumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceProd_Collumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        DateProd_Collumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        InvTabelView.setItems(inventoryListData);

    }

    public void inventorySelectData() {

        productData prodData = InvTabelView.getSelectionModel().getSelectedItem();
        int num = InvTabelView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
    }

    @FXML
    void Add_Click(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/mvn/AddPane.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Add Item"); // Title for the popup
            stage.setScene(scene);

            // Set the modality, so it blocks events to other windows
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the stage
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    void Delete_Click(ActionEvent event) {

    }

    @FXML
    void Edit_Click(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryShowData();
    }

}
