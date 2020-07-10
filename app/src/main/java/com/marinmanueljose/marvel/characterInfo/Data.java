package com.marinmanueljose.marvel.characterInfo;

import java.util.List;

public class Data {
    private List<Object> results;

    public List<Object> getResults() {
        return results;
    }

    public String imgURL(){
        return results.get(0).imgURL();
    }

}
