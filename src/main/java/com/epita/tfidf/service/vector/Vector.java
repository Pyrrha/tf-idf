package com.epita.tfidf.service.vector;

import java.util.ArrayList;

public class Vector {

    private ArrayList<String> differentTokens;
    private ArrayList<Integer> timesTokenUsed;
    private ArrayList<Integer> percentTokenUsed;

    public Vector(){
        this.differentTokens = new ArrayList<String>();
        this.timesTokenUsed = new ArrayList<Integer>();
        this.percentTokenUsed = new ArrayList<Integer>();
    }

}
