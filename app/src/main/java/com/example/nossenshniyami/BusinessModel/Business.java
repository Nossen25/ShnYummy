package com.example.nossenshniyami.BusinessModel;

public class Business {
    private String name;
    private String address;
    private String phoneNumber;
    private String Info; //  תקציר בקצרה של מידע על החנות מה הם מוכרים וכד
    private String imageURL; // מכיל את URL של התמונה בסטורג



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

    public String geStInfo() {
        return this.Info;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
