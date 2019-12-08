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
import thienVN.Common.Constraint;
import thienVN.JaxB.HouseDTO;
import thienVN.JaxB.UniversityDTO;
import thienVN.Utils.MyConnection;
import thienVN.Utils.TextUtils;
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

    public thienVN.JaxB.Houses getTop100Houses() throws ClassNotFoundException, SQLException {
        thienVN.JaxB.Houses houses = new thienVN.JaxB.Houses();
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
                    if (image == null) {
                        image = "";
                    }
                    url = rs.getString("url");
                    name = rs.getString("name");
                    address = rs.getString("address");
                    phone = rs.getString("phone");
                    if (phone == null) {
                        phone = "";
                    }
                    area = rs.getString("area");
                    electricPrice = rs.getString("electricPrice");
                    waterPrice = rs.getString("waterPrice");
                    rentPrice = rs.getString("rentPrice");
                    latitude = rs.getFloat("latitude");
                    longitude = rs.getFloat("longitude");
                    thienVN.JaxB.House dto = new thienVN.JaxB.House(id, image, url, name, address, phone, TextUtils.getNumberFromString(area), electricPrice, waterPrice, TextUtils.getNumberFromString(rentPrice), BigDecimal.valueOf(latitude), BigDecimal.valueOf(longitude));
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
            float latitude = 0;
            float longitude = 0;
            if (con != null) {
                String sql = "Insert into Home(image,url,name,address,phone,area,electricPrice, waterPrice, rentPrice, latitude, longitude)"
                        + " values(?,?,?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                latitude = dto.getLatitude().floatValue();
                longitude = dto.getLongitude().floatValue();
                stm.setString(1, dto.getImage());
                stm.setString(2, dto.getUrl());
                stm.setString(3, dto.getName());
                stm.setString(4, dto.getAddress());
                stm.setString(5, dto.getPhone());
                stm.setString(6, dto.getArea());
                stm.setString(7, dto.getElectricPrice());
                stm.setString(8, dto.getWaterPrice());
                stm.setString(9, dto.getRentPrice());
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
                String sql = "select id from Home where url = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, house.getUrl());
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
        String area, rentPrice;

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
                    area = rs.getString("area");
                    rentPrice = rs.getString("rentPrice");
                    HouseDTO dto = new HouseDTO(id, image, url, address, phone, area, rentPrice);
                    houses.add(dto);
                }
            }
        } finally {
            closeConnection();
        }

        return houses;
    }

    public void updateSelectedHome(String id) throws ClassNotFoundException, SQLException {
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "UPDATE Home SET time_selected = case when time_selected IS NOT NULL then time_selected +1 else 1 end where id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                stm.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }

    public boolean updateHouse(House dto) throws ClassNotFoundException, SQLException {
        boolean check = false;
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "Update Home set image=?,name = ?,phone=?,area=?,electricPrice=?, waterPrice=?, rentPrice=? where url =?";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getImage());
                stm.setString(2, dto.getName());
                stm.setString(3, dto.getPhone());
                stm.setString(4, dto.getArea());
                stm.setString(5, dto.getElectricPrice());
                stm.setString(6, dto.getWaterPrice());
                stm.setString(7, dto.getRentPrice());
                stm.setString(8, dto.getUrl());
                check = stm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    public ArrayList<HouseDTO> findHome(float latitude, float longitude, float distance, int weightDistance, int weightArea, int weightPrice) throws ClassNotFoundException, SQLException {
        ArrayList<HouseDTO> houses = new ArrayList<HouseDTO>();
        int id;
        String image, url, name, address, phone;
        String area, electricPrice, waterPrice, rentPrice;
        float distances;
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "select id,image,url,name,address,phone,area,electricPrice, waterPrice, rentPrice,( 6371 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) ) + sin( radians(?) ) * sin( radians( latitude ) ) ) ) as 'distance' FROM Home WHERE ( 6371 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) ) + sin( radians(?) ) * sin( radians( latitude ) ) ) ) < ?";
                stm = con.prepareStatement(sql);
                stm.setFloat(1, latitude);
                stm.setFloat(2, longitude);
                stm.setFloat(3, latitude);
                stm.setFloat(4, latitude);
                stm.setFloat(5, longitude);
                stm.setFloat(6, latitude);
                stm.setFloat(7, distance);
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
                    distances = rs.getFloat("distance");
                    if (rentPrice.equals("Thỏa thuận")) {
                        rentPrice = Constraint.RENT_PRICE_DEFAULT;
                    }
                    HouseDTO dto = new HouseDTO(id, image, url, address, phone, area, electricPrice, waterPrice, rentPrice, distances, weightDistance, weightArea, weightPrice);
                    houses.add(dto);
                }
            }
        } finally {
            closeConnection();
        }

        return houses;
    }

    public ArrayList<UniversityDTO> getListUniversity() throws ClassNotFoundException, SQLException {
        ArrayList<UniversityDTO> listUniverity = new ArrayList<>();
        try {
            String name = "";
            int id = 0;
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "SELECT id,name from University";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("id");
                    name = rs.getString("name");
                    UniversityDTO dto = new UniversityDTO(id, name);
                    listUniverity.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return listUniverity;
    }

    public int getNumberHomemate(int homeId, int universityId) throws SQLException, ClassNotFoundException {
        int number = 0;
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "SELECT number from Home_University WHERE homeId = ? and universityId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, homeId);
                stm.setInt(2, universityId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    number = rs.getInt("number");
                }
            }
        } finally {
            closeConnection();
        }
        return number;
    }

    public void updateHome_Universtity(int homeId, int universityId) throws ClassNotFoundException, SQLException {
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "UPDATE Home_University SET number = case when number IS NOT NULL then number +1 else 1 end where homeId = ? and universityId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, homeId);
                stm.setInt(2, universityId);
                stm.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }

    public boolean isHome_UniverstityExisted(int homeId, int universityId) throws ClassNotFoundException, SQLException {
        boolean check = false;
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "SELECT number from Home_University WHERE homeId = ? and universityId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, homeId);
                stm.setInt(2, universityId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insertHome_UniverstityExisted(int homeId, int universityId) throws ClassNotFoundException, SQLException {
        boolean check = false;
        try {
            con = MyConnection.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Home_University (homeId, universityId, number) values(?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, homeId);
                stm.setInt(2, universityId);
                stm.setInt(3, 1);
                stm.executeUpdate();
            }
        } finally {
            closeConnection();
        }
        return check;
    }
}
