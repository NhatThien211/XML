/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class MyConnection implements Serializable{
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Connection con =null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url ="jdbc:sqlserver://localhost:1433;databaseName=HomeSuggesstion";
        con = DriverManager.getConnection(url,"sa","12");
        return con;
    }
}
