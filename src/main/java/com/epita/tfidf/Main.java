package com.epita.tfidf;

import java.util.List;
import java.util.Map;
import com.epita.tfidf.service.parser.Parser;
import com.epita.tfidf.service.stemming.Stemming;
import com.epita.tfidf.service.stemming.StopWordsRemover;
import com.epita.tfidf.service.synonyms.Synonyms;
import com.epita.tfidf.service.tokenizer.Tokenizer;
import com.epita.tfidf.service.vector.Vector;
import com.epita.tfidf.service.vector.Vector.VectorValues;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(@NotNull String[] args) {
        //1 parse
        Parser parser = new Parser(args[0]);
        String parsedText = parser.parseHtml();

        //2 tokenize
        Tokenizer tokenizer = new Tokenizer(parsedText);
        List<String> tokens = tokenizer.process();

        //3 remove stop words
        StopWordsRemover stopWordsRemover = new StopWordsRemover();
        tokens = stopWordsRemover.process(tokens);

        //4 stem
        Stemming stemmer = new Stemming(tokens);
        tokens = stemmer.stem();

        //5 replace synonyms
        Synonyms synonyms = new Synonyms();
        tokens = synonyms.process(tokens);

        //6 vectorize
        Vector vector = new Vector(tokens);
        Map<String, VectorValues> index = vector.vectorize();

        //Result
        log.info("Result: " + index.toString());
    }
}
