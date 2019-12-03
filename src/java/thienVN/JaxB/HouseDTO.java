/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.JaxB;

/**
 *
 * @author ASUS
 */
public class HouseDTO {

    private int id;
    private String image;
    private String url;
    private String name;
    private String address;
    private String phone;
    private float area;
    private float electricPrice;
    private float waterPrice;
    private float rentPrice;
    private float latitude;
    private float longitude;

    public HouseDTO(int id, String image, String url, String name, String address, String phone, float area, float electricPrice, float waterPrice, float rentPrice, float latitude, float longitude) {
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

    public HouseDTO(int id, String image, String url, String address, String phone, float area, float rentPrice) {
        this.id = id;
        this.image = image;
        this.url = url;
        this.address = address;
        this.phone = phone;
        this.area = area;
        this.rentPrice = rentPrice;
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

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getElectricPrice() {
        return electricPrice;
    }

    public void setElectricPrice(float electricPrice) {
        this.electricPrice = electricPrice;
    }

    public float getWaterPrice() {
        return waterPrice;
    }

    public void setWaterPrice(float waterPrice) {
        this.waterPrice = waterPrice;
    }

    public float getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(float rentPrice) {
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

}
