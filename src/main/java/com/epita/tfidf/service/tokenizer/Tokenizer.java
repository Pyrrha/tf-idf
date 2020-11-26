package com.epita.tfidf.service.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Tokenizer {

    private String text;

    public Tokenizer(@NotNull String cleanText) {
        this.text = cleanText;
    }

    /**
     * Create an array of tokens from a clean text. Split regex captures single
     * points (.), comas(,), semicolons (;), quotation marks ("), and spaces
     * with optionally a dash or an apostrophe aside.
     * @return List a list of tokens that can be use to index
     */
    public List<String> process() {
        String lowerText = this.text.toLowerCase();
        return new ArrayList<>(Arrays.asList(lowerText.split("[.,;\"]|(['-]* ['-]*)")));
    }
}
