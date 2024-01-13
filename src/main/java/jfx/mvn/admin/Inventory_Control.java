package jfx.mvn.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfx.mvn.KonektorSQL;
import jfx.mvn.data;
import jfx.mvn.productData;
import javafx.scene.image.Image;

public class Inventory_Control implements Initializable {

    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    public TableView<productData> InvTabelView;

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
        productData selectedProduct = InvTabelView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            int Id = selectedProduct.getId();
            String ProdID = selectedProduct.getProductId();

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: " + ProdID + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String DeleteData = "DELETE FROM product WHERE id= " + Id;
                try {
                    prepare = connect.prepareStatement(DeleteData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    inventoryShowData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }

        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("No product selected for deletion.");
            alert.showAndWait();
        }
    }

    @FXML
    void Edit_Click(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/mvn/UpdatePane.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("edit Item"); // Title for the popup
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryShowData();

        // InvTabelView.setOnMouseClicked(event -> {
        // if (event.getClickCount() > 0) {
        // productData selectedProduct =
        // InvTabelView.getSelectionModel().getSelectedItem();
        // if (selectedProduct != null) {

        // productId = selectedProduct.getProductId();

        // // You can use these values to do whatever you need to do with them
        // System.out.println("Selected Product ID: " + productId);
        // }
        // }
        // });
    }

}
