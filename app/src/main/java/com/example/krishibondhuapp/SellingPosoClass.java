package com.example.krishibondhuapp;

public class SellingPosoClass {
    private String category,productName,unit,quantity,price,jat,placeName,imageUrl,date,time,userPic,userName,sellingPostKey,userPhoneNumber;

    public SellingPosoClass() {
    }

    public SellingPosoClass(String category, String productName, String unit, String quantity, String price, String jat, String placeName, String imageUrl, String date, String time, String userPic, String userName, String sellingPostKey, String userPhoneNumber) {
        this.category = category;
        this.productName = productName;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.jat = jat;
        this.placeName = placeName;
        this.imageUrl = imageUrl;
        this.date = date;
        this.time = time;
        this.userPic = userPic;
        this.userName = userName;
        this.sellingPostKey = sellingPostKey;
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJat() {
        return jat;
    }

    public void setJat(String jat) {
        this.jat = jat;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSellingPostKey() {
        return sellingPostKey;
    }

    public void setSellingPostKey(String sellingPostKey) {
        this.sellingPostKey = sellingPostKey;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}
