package com.example.krishibondhuapp;

public class AgriOfficerPosoClass {
    private String imageUrl;
    private String name;
    private String designation;
    private String location;
    private String phone;

    public AgriOfficerPosoClass() {
    }

    public AgriOfficerPosoClass(String imageUrl, String name, String designation, String location, String phone) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.designation = designation;
        this.location = location;
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
