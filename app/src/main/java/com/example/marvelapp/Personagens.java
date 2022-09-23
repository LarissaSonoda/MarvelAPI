package com.example.marvelapp;

import java.io.Serializable;

public class Personagens implements Serializable {
    private String CharacterCode, CharacterName;

    public Personagens(){ }

    public Personagens(String characterCode, String characterName){
        CharacterCode = characterCode;
        CharacterName = characterName;
    }

    public String getCharacterCode() {return CharacterCode;}

    public void setCharacterCode(String characterCode) {
        CharacterCode = characterCode;
    }

    public String getCharacterName() {
        return CharacterName;
    }

    public void setCharacterName(String characterName) {
        CharacterName = characterName;
    }
}
