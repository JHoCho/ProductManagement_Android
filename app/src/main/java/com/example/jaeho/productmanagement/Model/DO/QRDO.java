package com.example.jaeho.productmanagement.Model.DO;

/**
 * Created by jaeho on 2017. 8. 22..
 */

public class QRDO {
    private String productName;
    private String detailedProductName;
    private String serialNumber;
    private String companyName;
    private String building;
    private String floor;
    private String roomName;
    private String date;
    private String adminID;
    private String price;
    private String location;
    private String outDate;
    public QRDO(){

    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public QRDO(String s){
        setLocation();
    }
    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDetailedProductName() {
        return detailedProductName;
    }

    public void setDetailedProductName(String detailedProductName) {
        this.detailedProductName = detailedProductName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getLocation() {
        if (location.equals(null)){
            System.out.println(companyName+building+floor+roomName);
            return companyName+building+floor+roomName;
        }else {
            return location;
        }
    }

    public void setLocation() {
        if(companyName!=null&&building!=null&&floor!=null&&roomName!=null) {
            this.location = companyName+building+floor+roomName;
        }else {
            location="회사명,빌딩,층,방이름(번호)중 하나 이상이 비어있어 초기화하지 못했습니다.";
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
