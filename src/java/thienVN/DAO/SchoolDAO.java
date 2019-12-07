/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import thienVN.Utils.MyConnection;

/**
 *
 * @author ASUS
 */
public class SchoolDAO {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public boolean isSchoolExisted(String name) throws ClassNotFoundException, SQLException {
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "select id from University where name=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public void insertSchool(String name) throws ClassNotFoundException, SQLException {
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "INSERT INTO University(name) values (?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                stm.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }
}
