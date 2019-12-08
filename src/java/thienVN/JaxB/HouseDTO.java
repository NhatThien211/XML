/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.JaxB;

import java.io.Serializable;
import java.text.DecimalFormat;
import thienVN.Common.Constraint;
import thienVN.Utils.TextUtils;

/**
 *
 * @author ASUS
 */
public class HouseDTO implements Comparable<HouseDTO>, Serializable {

    private int id;
    private String image;
    private String url;
    private String name;
    private String address;
    private String phone;
    private String area;
    private String electricPrice;
    private String waterPrice;
    private String rentPrice;
    private float latitude;
    private float longitude;
    private float distance;
    private float point;
    private int numberHomemate;

    public HouseDTO(int id, String image, String url, String name, String address, String phone, String area, String electricPrice, String waterPrice, String rentPrice, float latitude, float longitude) {
        this.id = id;
        this.image = image;
        this.url = url;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.area = area;
        this.electricPrice = electricPrice;
        this.waterPrice = waterPrice;
        this.rentPrice = rentPrice;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public HouseDTO(int id, String image, String url, String address, String phone, String area, String rentPrice) {
        this.id = id;
        this.image = image;
        this.url = url;
        this.address = address;
        this.phone = phone;
        this.area = area;
        this.rentPrice = rentPrice;
    }

    public HouseDTO(int id, String image, String url, String address, String phone, String area, String electricPrice, String waterPrice,
            String rentPrice, float distance, int WeightedDistance, int weightedArea, int weightedPrice) {
        this.id = id;
        this.image = image;
        this.url = url;
        this.address = address;
        this.phone = phone;
        this.area = area;
        this.electricPrice = electricPrice;
        this.waterPrice = waterPrice;
        this.rentPrice = rentPrice;
        this.distance = distance;
        float points = WeightedDistance * (Constraint.RADIUS - distance) + weightedArea * calculateAreaPoint();
        this.point = points / getCost(weightedPrice);
    }

    public String getDistance() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.distance);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getNumberHomemate() {
        return numberHomemate;
    }

    public void setNumberHomemate(int numberHomemate) {
        this.numberHomemate = numberHomemate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public String getElectricPrice() {
        return electricPrice;
    }

    public String getWaterPrice() {
        return waterPrice;
    }

    public String getRentPrice() {
        if (this.rentPrice.contains("triệu")) {
            return rentPrice;
        } else {
            return rentPrice + " triệu/tháng";
        }
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setElectricPrice(String electricPrice) {
        this.electricPrice = electricPrice;
    }

    public void setWaterPrice(String waterPrice) {
        this.waterPrice = waterPrice;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getPoint() {
        // 0km -> 10 point, 2 km -> 8 point 
        return this.point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    private float getCost(int weightedPrice) {
        float water = 0;
        float electric = 0;
        if (this.waterPrice != null) {
            if (this.waterPrice.contains(Constraint.WATER_UNIT_ALL)) {
                // if 100K/nguoi
                water = Float.parseFloat(TextUtils.getNumberFromString(waterPrice));
            } else if (this.waterPrice.contains(Constraint.WATER_UNIT_M3)) {
                // if 20K/m3 => 20 * average water use in 1 month
                water = Float.parseFloat(TextUtils.getNumberFromString(waterPrice));
                water = water * Constraint.AVERAGE_WATER;
            }
        } else {
            //  100K/nguoi
            water = 100;
        }
        if (this.electricPrice != null) {
            if (this.electricPrice.contains(Constraint.ELECTRIC_UNIT_ALL)) {
                // 5k/kw
                electric = Float.parseFloat(TextUtils.getNumberFromString(this.electricPrice));
            }
        } else {
            // 5k/kwt
            electric = 5;
        }
        float temp = 0;
        try {
            float rent = Float.parseFloat(TextUtils.getNumberFromString(rentPrice));
            temp = water / 1000 + electric / 1000 + rent / weightedPrice;
        } catch (Exception e) {
            System.out.println(this.id);
        }
        return temp;
    }

    private float calculateAreaPoint() {
        //50m -> 10 point
        return (Float.parseFloat(TextUtils.getNumberFromString(this.area)) * 10) / 50;
    }

    @Override
    public int compareTo(HouseDTO o) {
        float point = o.getPoint();
        return Float.compare(o.getPoint(), this.point);
    }
}
