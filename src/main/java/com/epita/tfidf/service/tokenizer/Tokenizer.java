package com.epita.tfidf.service.tokenizer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public static List<Token> process(@NotNull String cleanText) {
        final List <Token> listOfTokens = new ArrayList<>();
        String lowerText = cleanText.toLowerCase();

        for (String word : lowerText.split("[ .,;]"))
            listOfTokens.add(new Token(word));

        // delete stop words
        // steamming
        // synonymes
        return listOfTokens;
    }
}
