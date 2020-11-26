package com.epita.tfidf.service.synonyms;

import lombok.Data;
import java.util.List;

@Data
public class Word {

    private String word;
    private WordData data;

    @Data
    public class WordData {
        private Integer nb;
        private List<String> synonyms;
    }
}
