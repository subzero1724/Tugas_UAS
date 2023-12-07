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
    private TextField Email_Addres;

    @FXML
    private TextField Password;

    @FXML
    private ImageView img;

    @FXML
    void L_Button(ActionEvent event) {
        try {
            String Username = Email_Addres.getText();
            String Pass = Password.getText();

            stat = con.createStatement();
            String sql = "select * from register_user where Email = '" + Username + "' and Password = '" + Pass + "'";

            ResultSet rs = stat.executeQuery(sql);

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Berhasil");

                // App.setRoot("Dashboard");
                // Load the Dashboard.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root = loader.load();

                // Get the current stage from one of the components
                Stage stage = (Stage) Email_Addres.getScene().getWindow();

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
                Email_Addres.setText("");
                Password.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Email_Addres.getText());
        System.out.println(Password.getText());
    }

    void insert() {
        try {
            stat = con.createStatement();
            stat.executeUpdate("insert into register_user values('" + Email_Addres.getText() + "','"
                    + Password.getText() + "')");
            JOptionPane.showMessageDialog(null, "Insert Successfully");
            Email_Addres.setText("");
            Password.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Sign_Up(MouseEvent event) {
        insert();
        System.out.println("Hello World");
    }

}
