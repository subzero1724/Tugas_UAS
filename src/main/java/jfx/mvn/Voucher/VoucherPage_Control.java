package jfx.mvn.Voucher;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jfx.mvn.KonektorSQL;
import jfx.mvn.admin.AddPane_Control;

public class VoucherPage_Control implements Initializable {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private ScrollPane Scroll_Voucher;

    @FXML
    private GridPane VoucherGrid;

    public int getNextVoucherId() {
        int nextVoucherID = 1;

        try {
            connect = KonektorSQL.connectDB();
            String sql = "SELECT MAX(CAST(SUBSTRING(customer_id, LOCATE('-', customer_id) + 1) AS SIGNED)) AS no FROM register_user WHERE customer_id LIKE ?";
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, "CST-%");
            result = preparedStatement.executeQuery();

            if (result.next()) {
                nextVoucherID = result.getInt("no") + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPane_Control.class.getName()).log(Level.SEVERE, null, ex);
        }

        return nextVoucherID;
    }

    public void DisplayCard() {
        int collumn = 0;
        int row = 0;
        VoucherGrid.getChildren().clear();
        // VoucherGrid.getRowConstraints().clear();
        // VoucherGrid.getColumnConstraints().clear();
        try {
            for (int i = 0; i < 1; i++) {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/jfx/mvn/VoucherCard.fxml"));
                AnchorPane pane = load.load();

                if (collumn == 1) {
                    collumn = 0;
                    row += 1;
                }
                VoucherGrid.add(pane, 1, 1);
                GridPane.setMargin(pane, new Insets(10));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DisplayCard();
    }

}
