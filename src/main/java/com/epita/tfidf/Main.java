package com.epita.tfidf;

import com.epita.tfidf.service.parser.Parser;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(@NotNull String[] args) {
        Parser parser = new Parser(args[0]);
        String parsedText = parser.parseHtml();
        //Tokenizer tokenizer = new Tokenizer(parsedText);
    }
}
