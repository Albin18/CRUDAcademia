
package com.ch.crudregistro.Conexion;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;

public class Mysql {
    
     Connection conectar = null;
    String user = "root";
    String password = "admin";
    String bd = "escuela";
    String url="jdbc:mysql://localhost:3306";
    String cadena = url+"/"+bd;
    
    public Connection establecerConexion(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar=DriverManager.getConnection(cadena, user, password);
           // JOptionPane.showMessageDialog(null, "Se conecto a la BD");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se conecto a la BD" + ex.toString());
        }
        return conectar;
    }
    
}
