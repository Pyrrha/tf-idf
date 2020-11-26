package com.epita.tfidf.service.synonyms;

import lombok.Data;

import java.util.List;

@Data
public class Word {

    private String name;
    private Integer nb;
    private List<String> listOfSynonyms;

}
