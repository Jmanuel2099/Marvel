package com.marinmanueljose.marvel.characterInfo;

public class CharacterInfo {
    private Data data;

    public Data getData() {
        return data;
    }

    public String imgURL(){
        return this.data.imgURL();
    }
}
