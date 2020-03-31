package com.example.krishibondhuapp;

public class PostPosoClass {

    private String Text;
    private String Imageurl;
    private String userName;
    private String userPic;
    private String date;
    private String time;
    private String postKey;
    private String audiolink;


    public PostPosoClass() {
    }

    public PostPosoClass(String text, String imageurl, String userName, String userPic, String date, String time, String postKey, String audiolink) {
        Text = text;
        Imageurl = imageurl;
        this.userName = userName;
        this.userPic = userPic;
        this.date = date;
        this.time = time;
        this.postKey = postKey;
        this.audiolink = audiolink;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
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

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getAudiolink() {
        return audiolink;
    }

    public void setAudiolink(String audiolink) {
        this.audiolink = audiolink;
    }
}
