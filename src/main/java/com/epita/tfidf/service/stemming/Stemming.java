package com.epita.tfidf.service.stemming;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Stemming
 */
public class Stemming {

    private List<String> tokens;
    private PorterStemmer stemmer;

    public Stemming(List<String> tokens) {
        this.tokens = tokens;
        this.stemmer = new PorterStemmer();
    }

    /**
     * Transform each token to its stemmed form.
     * @return the list of stemmed words
     */
    public List<String> stem() {
        return this.tokens.stream()
                .map(this::getRootForm)
                .collect(Collectors.toList());
    }

    /**
     * Parse the given word to its root form with the Porter Stemmer
     * algorithm.
     * @param word word to stem
     * @return stemmed word
     */
    private String getRootForm(String word) {
        String res;
        this.stemmer.add(word);
        this.stemmer.stem();
        res = this.stemmer.toString();
        this.stemmer.clear();
        return res;
    }
}
