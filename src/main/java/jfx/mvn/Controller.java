package jfx.mvn;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public Controller() {
    }

    @FXML
    private TextField Username_Box;

    @FXML
    private TextField Password;

    @FXML
    private ImageView img;

    @FXML
    void L_Button(ActionEvent event) {

        if (Username_Box.getText().isEmpty() || Password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } else {
            String selectData = "select Username,Password from register_user where Username = ? and Password = ?";
            connect = KonektorSQL.connectDB();

            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, Username_Box.getText());
                prepare.setString(2, Password.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    data.username = Username_Box.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

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
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
