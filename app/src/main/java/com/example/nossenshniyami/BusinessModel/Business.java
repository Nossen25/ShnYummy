package com.example.nossenshniyami.BusinessModel;

public class Business {
    private String name;
    private String address;
    private String phoneNumber;
    private int imgRes;

    public Business(String name, String address, String phoneNumber, int imgRes) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.imgRes = imgRes;
    }

    public Business(){}

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
