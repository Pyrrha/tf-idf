package com.epita.tfidf.service.stemming;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopWordsRemover {

    private final Logger log = LoggerFactory.getLogger(StopWordsRemover.class);
    private final List<String> stopWords;

    /**
     * Constructor ; gets the stop words from the dedicated file and
     * turn it into a list.
     * The file is located in "src/main/resources/stop-words/stop-words_english_5_en.txt"
     */
    public StopWordsRemover() {
        List<String> list = new ArrayList<>();
        File stopWordsFile = new File("src/main/resources/stop-words/stop-words_english_5_en.txt");
        BufferedReader reader;

        try (FileReader fileReader = new FileReader(stopWordsFile)) {
            reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }

        this.stopWords = list;
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
