package com.example.marvelapp;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String UserCode, UserName, UserEmail, UserPassword;
    private byte[] UserImage;

    public Usuario(){ }

    public Usuario(String userCode, String userName, String userEmail, String userPassword, byte[] userImage){
        UserCode = userCode;
        UserName = userName;
        UserEmail = userEmail;
        UserPassword = userPassword;
        UserImage = userImage;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public byte[] getUserImage() {
        return UserImage;
    }

    public void setUserImage(byte[] userImage) {
        UserImage = userImage;
    }
}