package com.marinmanueljose.marvel.characterInfo;

public class ImgURL {
    private String path = "";
    private String extension = "";

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }

    public String url (){
        return this.path + "." + this.extension;
    }
}
