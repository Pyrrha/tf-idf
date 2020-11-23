package com.epita.tfidf.service.tokenizer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    /**
     * Create an array of tokens from a clean text
     * @param cleanText String result of a scrapping action
     * @return List a list of tokens that can be use to index
     */
    public static List<Token> process(@NotNull String cleanText) {
        final List <Token> listOfTokens = new ArrayList<>();
        String lowerText = cleanText.toLowerCase();

        // regex captures single points (.), comas(,), semicolons (;), quotation marks ("),
        // and spaces with optionally a dash or an apostrophe aside.
        for (String word : lowerText.split("[.,;\"]|(['-]* ['-]*)"))
            listOfTokens.add(new Token(word));

        return listOfTokens;
    }
}
