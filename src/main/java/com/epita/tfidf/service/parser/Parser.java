package com.epita.tfidf.service.parser;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Class Parser
 */
public class Parser {

    private final String url;

    /**
     * Constructor
     * @param url html document's url to parse
     */
    public Parser(String url) {
        this.url = url;
    }

    /**
     * Function to parse HTML
     * @return String with data of the url
     */
    public String parseHtml() {
        String content = "";

        try {
            Document document = Jsoup.connect(this.url).get();
            Document body = Jsoup.parseBodyFragment(document.body().html());
            content = body.text();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public static void main(@NotNull String[] args) {
        Parser parser = new Parser(args[0]);
        System.out.println(parser.parseHtml());
    }
}
