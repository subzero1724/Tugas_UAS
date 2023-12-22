package jfx.mvn;

import java.sql.*;
import javax.swing.JOptionPane;

public class KonektorSQL {

    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_rapuri", "root", "");
            return connect;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal" + e.getMessage());
        }
        return null;
    }
}
