package jfx.mvn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class signup_control {
    Connection con;
    Statement stat;
    ResultSet rs;
    String sql;

    public signup_control() {
        KonektorSQL DB = new KonektorSQL();
        DB.connect();
        con = DB.con;
        stat = DB.stm;
    }

    @FXML
    private TextField Email_B;

    @FXML
    private TextField Password_B;

    @FXML
    private Button SignUp_Button;

    @FXML
    private TextField Username_B;

    @FXML
    void SignUp_action(ActionEvent event) {
        try {
            // Load Login_Page.fxml instead of signUp.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_Page.fxml"));
            Parent root = loader.load();

            // Get the current stage from one of the components
            Stage stage = (Stage) Email_B.getScene().getWindow();

            // Set the scene to Login_Page
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
        insert();
    }

    void insert() {
        try {
            stat = con.createStatement();
            stat.executeUpdate("insert into register_user values('" + Username_B.getText() + "','"
                    + Email_B.getText() + "','"
                    + Password_B.getText() + "')");
            JOptionPane.showMessageDialog(null, "Insert Successfully");
            Username_B.setText("");
            Email_B.setText("");
            Password_B.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
