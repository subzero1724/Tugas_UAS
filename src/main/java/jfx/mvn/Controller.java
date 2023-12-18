package jfx.mvn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
    Connection con;
    Statement stat;
    ResultSet rs;
    String sql;

    public Controller() {
        KonektorSQL DB = new KonektorSQL();
        DB.connect();
        con = DB.con;
        stat = DB.stm;
    }

    @FXML
    private TextField Username_Box;

    @FXML
    private TextField Password;

    @FXML
    private ImageView img;

    @FXML
    void L_Button(ActionEvent event) {
        try {
            String Username = Username_Box.getText();
            String Pass = Password.getText();

            stat = con.createStatement();
            String sql = "select * from register_user where Username = '" + Username + "' and Password = '" + Pass
                    + "'";

            ResultSet rs = stat.executeQuery(sql);

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Berhasil");

                // App.setRoot("Dashboard");
                // Load the Dashboard.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root = loader.load();

                // Get the current stage from one of the components
                Stage stage = (Stage) Username_Box.getScene().getWindow();

                // Set the scene to Dashboard
                stage.setScene(new Scene(root));
                // Center the stage on the screen
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                double centerX = bounds.getMinX() + (bounds.getWidth() / 2);
                double centerY = bounds.getMinY() + (bounds.getHeight() / 2);
                stage.setX(centerX - (stage.getWidth() / 2));
                stage.setY(centerY - (stage.getHeight() / 2));

                stage.show();
            }

            else {
                JOptionPane.showMessageDialog(null, "Login Gagal");
                Username_Box.setText("");
                Password.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Username_Box.getText());
        System.out.println(Password.getText());
    }

    @FXML
    void Sign_Up(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signUp.fxml"));
            Parent root = loader.load();

            // Get the current stage from one of the components
            Stage stage = (Stage) Username_Box.getScene().getWindow();

            // Set the scene to Dashboard
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // insert();
        System.out.println("Hello World");
    }

}
