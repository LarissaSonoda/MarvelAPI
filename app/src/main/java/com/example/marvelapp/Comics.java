package com.example.marvelapp;

import java.io.Serializable;

public class Comics implements Serializable {
    private String ComicsCode, ComicsName;

    public Comics(){ }

    public Comics(String comicsCode, String comicsName){
        ComicsCode = comicsCode;
        ComicsName = comicsName;
    }

    public String getComicsCode() {
        return ComicsCode;
    }

    public void setComicsCode(String comicsCode) {
        ComicsCode = comicsCode;
    }

    public String getComicsName() {
        return ComicsName;
    }

    public void setComicsName(String comicsName) {
        ComicsName = comicsName;
    }

}
