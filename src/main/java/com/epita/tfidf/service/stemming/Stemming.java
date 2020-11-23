package com.epita.tfidf.service.stemming;

import com.epita.tfidf.service.tokenizer.Token;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Stemming
 */
public class Stemming {

    private List<String> tokens;

    public Stemming(List<String> tokens) {
        this.tokens = tokens;
    }

    /**
     * Transform each token to its stemmed form.
     * @return the list of stemmed words
     */
    public List<String> stem() {
        return this.tokens.stream()
                .map(Stemming::getRootForm)
                .collect(Collectors.toList());
    }

    /**
     * Parse the given word to its root form.
     * @param word word to stem
     * @return stemmed word
     */
    private static String getRootForm(String word) {
        //TODO get the stem word
        return word;
    }
}
