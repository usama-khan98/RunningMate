package com.chatapp.app;

public class postModel {
    String postTitle;
    String postLocation;
    String postGender;
    String postAge;
    String postTime;

    public postModel(String postTitle, String postLocation, String postGender, String postAge, String postTime) {
        this.postTitle = postTitle;
        this.postLocation = postLocation;
        this.postGender = postGender;
        this.postAge = postAge;
        this.postTime = postTime;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostLocation() {
        return postLocation;
    }

    public String getPostGender() {
        return postGender;
    }

    public String getPostAge() {
        return postAge;
    }

    public String getPostTime() {
        return postTime;
    }
}
