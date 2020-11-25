package com.epita.tfidf.service.stemming;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StopWordsRemover {

    private final String STOP_WORDS_FILE_PATH = "src/main/resources/stop-words/stop-words_english_5_en.txt";
    private final List<String> stopWords;

    /**
     * Constructor ; gets the file containing the stop words and turn it into a list.
     */
    public StopWordsRemover() {
        List<String> stopWords = new ArrayList<>();
        File stopWordsFile = new File(STOP_WORDS_FILE_PATH);
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(stopWordsFile));
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            this.stopWords = Collections.emptyList();
            return;
        }

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line);
            }
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }

        this.stopWords = stopWords;
    }

    /**
     * Filters the tokens by keeping only those who are not stop-words.
     * @param tokens the list of tokens to filter
     * @return the filtered tokens list
     */
    public List<String> process(List<String> tokens) {
        return tokens.stream()
                .filter(token -> !this.stopWords.contains(token))
                .collect(Collectors.toList());
    }
}
