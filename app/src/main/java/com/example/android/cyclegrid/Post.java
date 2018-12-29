package com.example.android.cyclegrid;

/**
 * Created by Prashanthi Bala on 9/3/2018.
 */

public class Post {
    public String mailId;
    public String userName;
    public String password;
    public String postID;
    public String uID;
    public Post(){

    }

    public Post(String mail, String un, String pass, String postID, String uID) {
        this.mailId = mail;
        this.userName = un;
        this.password = pass;
        this.postID = postID;
        this.uID = uID;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mail) {
        this.mailId = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
