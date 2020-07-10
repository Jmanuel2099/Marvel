package com.marinmanueljose.marvel.characterInfo;


public class Object {
    private ImgURL thumbnail ;

    public ImgURL getThumbnail() {
        return thumbnail;
    }

    public String imgURL(){
        return this.thumbnail.url();
    }

}
