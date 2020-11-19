package com.epita.tfidf.service.tokenizer;

import java.util.List;
import java.util.stream.Collectors;

public class Stemming {

    /**
     * Transform each word to its stemmed form.
     * @param words the list of words to transform
     * @return the list of stemmed words
     */
    public static List<Token> stem(List<Token> words) {
        return words.stream().map(Stemming::getCanonicalForm).collect(Collectors.toList());
    }

    /**
     * Parse the given word to its stem form.
     * @param word word to get the stem from
     * @return stem word
     */
    private static Token getCanonicalForm(Token word) {
        return word; //TODO get the stem word
    }
}
