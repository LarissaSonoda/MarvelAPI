package com.example.marvelapp;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String UserCode, UserName, UserPassword;

    public Usuario(){ }

    public Usuario(String userCode, String userName, String userPassword){
        UserCode = userCode;
        UserName = userName;
        UserPassword = userPassword;
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

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

}