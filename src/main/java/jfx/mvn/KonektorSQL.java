package jfx.mvn;

import java.sql.*;
import javax.swing.JOptionPane;

public class KonektorSQL {
    Connection con;
    Statement stm;
    
    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_rapuri","root","");
            stm = con.createStatement();
            JOptionPane.showMessageDialog(null,"Connection Succes");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Koneksi Gagal"+ e.getMessage());
        }
    }
}
