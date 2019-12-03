/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.DAO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import thienVN.JaxB.HouseDTO;
import thienVN.Utils.MyConnection;
import xsd.thien.House;
import xsd.thien.Houses;

/**
 *
 * @author ASUS
 */
public class HomeDAO implements Serializable {

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

    public Houses getTop100Houses() throws ClassNotFoundException, SQLException {
        Houses houses = new Houses();
        int id;
        String image, url, name, address, phone;
        String area, electricPrice, waterPrice, rentPrice;
        float latitude, longitude;
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "SELECT top 100 id,image,url,name,address,phone,area,electricPrice, waterPrice, rentPrice, latitude, longitude FROM Home order by time_selected DESC";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("id");
                    image = rs.getString("image");
                    url = rs.getString("url");
                    name = rs.getString("name");
                    address = rs.getString("address");
                    phone = rs.getString("phone");
                    area = rs.getString("area");
                    electricPrice = rs.getString("electricPrice");
                    waterPrice = rs.getString("waterPrice");
                    rentPrice = rs.getString("rentPrice");
                    latitude = rs.getFloat("latitude");
                    longitude = rs.getFloat("longitude");
                    House dto = new House(id, image, url, name, address, phone, area + "", electricPrice, waterPrice, rentPrice, BigDecimal.valueOf(latitude), BigDecimal.valueOf(longitude));
                    houses.getHouse().add(dto);
                }
            }
        } finally {
            closeConnection();
        }

        return houses;
    }

    public boolean insertHouse(House dto) throws ClassNotFoundException, SQLException {
        boolean check = false;
        try {
            con = MyConnection.getConnection();
            float electricPrice = 0;
            float waterPrice = 0;
            float rentPrice = 0;
            float latitude = 0;
            float longitude = 0;
            float area = 0;
            if (con != null) {
                String sql = "Insert into Home(image,url,name,address,phone,area,electricPrice, waterPrice, rentPrice, latitude, longitude)"
                        + " values(?,?,?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                area = Float.parseFloat(dto.getArea());
                if (dto.getElectricPrice() != null && !dto.getElectricPrice().equals("")) {
                    electricPrice = Float.parseFloat(dto.getElectricPrice());
                }
                if (dto.getWaterPrice() != null && !dto.getWaterPrice().equals("")) {
                    waterPrice = Float.parseFloat(dto.getWaterPrice());
                }
                rentPrice = Float.parseFloat(dto.getRentPrice());
                latitude = dto.getLatitude().floatValue();
                longitude = dto.getLongitude().floatValue();
                stm.setString(1, dto.getImage());
                stm.setString(2, dto.getUrl());
                stm.setString(3, dto.getName());
                stm.setString(4, dto.getAddress());
                stm.setString(5, dto.getPhone());
                stm.setFloat(6, area);
                stm.setFloat(7, electricPrice);
                stm.setFloat(8, waterPrice);
                stm.setFloat(9, rentPrice);
                stm.setFloat(10, latitude);
                stm.setFloat(11, longitude);
                check = stm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    public Houses checkBeforeInsert(Houses houses) {
        ArrayList<House> listHouses = (ArrayList<House>) houses.getHouse();
        for (int i = 0; i < listHouses.size(); i++) {
            House dto = listHouses.get(i);
            if (dto.getName() == null || dto.getName().equals("") || dto.getRentPrice() == null || dto.getRentPrice().equals("") || dto.getUrl() == null || dto.getUrl().equals("")) {
                houses.getHouse().remove(i);
            }
        }
        return houses;
    }

    public boolean isHomeExisted(House house) throws ClassNotFoundException, SQLException {
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "select id from Home where url = ? or (url != ? and rentPrice = ? and latitude = ? and longitude = ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, house.getUrl());
                stm.setString(2, house.getUrl());
                stm.setFloat(3, Float.parseFloat(house.getRentPrice()));
                stm.setFloat(4, house.getLatitude().floatValue());
                stm.setFloat(5, house.getLongitude().floatValue());
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            return true;
        } finally {
            closeConnection();
        }
        return false;
    }

    public ArrayList<HouseDTO> searchHome(String searchValue) throws ClassNotFoundException, SQLException {
        ArrayList<HouseDTO> houses = new ArrayList<HouseDTO>();
        int id;
        String image, url, address, phone;
        float area, rentPrice;

        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "SELECT id,image,url,address,phone,area, rentPrice FROM Home where address LIKE ? order by time_selected DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("id");
                    image = rs.getString("image");
                    url = rs.getString("url");
                    address = rs.getString("address");
                    phone = rs.getString("phone");
                    area = rs.getFloat("area");
                    rentPrice = rs.getFloat("rentPrice");
                    HouseDTO dto = new HouseDTO(id, image, url, address, phone, area, rentPrice);
                    houses.add(dto);
                }
            }
        } finally {
            closeConnection();
        }

        return houses;
    }
}
