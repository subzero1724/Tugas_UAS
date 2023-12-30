package jfx.mvn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jfx.mvn.admin.AddPane_Control;

public class signup_control {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public signup_control() {
    }

    @FXML
    private TextField Email_B;

    @FXML
    private TextField Password_B;

    @FXML
    private Button SignUp_Button;

    @FXML
    private TextField Username_B;

    private Alert alert;

    public int getNextCustomerId() {
        int nextCustomerId = 1;

        try {
            connect = KonektorSQL.connectDB();
            String sql = "SELECT MAX(CAST(SUBSTRING(customer_id, LOCATE('-', customer_id) + 1) AS SIGNED)) AS no FROM register_user WHERE customer_id LIKE ?";
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, "CST-%");
            result = preparedStatement.executeQuery();

            if (result.next()) {
                nextCustomerId = result.getInt("no") + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPane_Control.class.getName()).log(Level.SEVERE, null, ex);
        }

        return nextCustomerId;
    }

    @FXML
    void SignUp_action(ActionEvent event) {
        if (Username_B.getText().isEmpty() || Password_B.getText().isEmpty() || Email_B.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error Message", "Please fill all blank fields");
        } else {
            int nextCustomerId = getNextCustomerId();
            String regData = "INSERT INTO register_user (customer_id,Username, Email, Password) VALUES (?, ?, ?, ?)";
            String checkUsername = "SELECT Username FROM register_user WHERE Username = ?";

            try (Connection connect = KonektorSQL.connectDB();
                    PreparedStatement checkStmt = connect.prepareStatement(checkUsername)) {

                checkStmt.setString(1, Username_B.getText());
                try (ResultSet result = checkStmt.executeQuery()) {
                    if (result.next()) {
                        showAlert(AlertType.ERROR, "Error Message", Username_B.getText() + " is already taken");
                        Username_B.setText("");
                        Email_B.setText("");
                        Password_B.setText("");
                    } else if (Password_B.getText().length() < 8) {
                        showAlert(AlertType.ERROR, "Error Message",
                                "Invalid Password, at least 8 characters are needed");
                        Username_B.setText("");
                        Email_B.setText("");
                        Password_B.setText("");
                    } else {
                        try (PreparedStatement insertStmt = connect.prepareStatement(regData)) {
                            insertStmt.setString(1, "CST-" + String.format("%03d", nextCustomerId));
                            insertStmt.setString(2, Username_B.getText());
                            insertStmt.setString(3, Email_B.getText());
                            insertStmt.setString(4, Password_B.getText());

                            insertStmt.executeUpdate();
                            showAlert(AlertType.INFORMATION, "Information Message", "Successfully registered Account!");
                            Username_B.setText("");
                            Email_B.setText("");
                            Password_B.setText("");
                            // Load Login_Page.fxml instead of signUp.fxml
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_Page.fxml"));
                            Parent root = loader.load();

                            // Get the current stage from one of the components
                            Stage stage = (Stage) Email_B.getScene().getWindow();

                            // Set the scene to Login_Page
                            stage.setScene(new Scene(root));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // void insert() {
    // try {
    // stat = con.createStatement();
    // stat.executeUpdate("insert into register_user values('" +
    // Username_B.getText() + "','"
    // + Email_B.getText() + "','"
    // + Password_B.getText() + "')");
    // JOptionPane.showMessageDialog(null, "Insert Successfully");
    // Username_B.setText("");
    // Email_B.setText("");
    // Password_B.setText("");
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

}
