package com.example.krishibondhuapp;

public class CommentPosoClass {

    private String imgUrl;
    private String name;
    private String comment;

    public CommentPosoClass() {
    }

    public CommentPosoClass(String imgUrl, String name, String comment) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.comment = comment;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
