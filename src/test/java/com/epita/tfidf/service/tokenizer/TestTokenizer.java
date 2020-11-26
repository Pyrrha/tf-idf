package com.epita.tfidf.service.tokenizer;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTokenizer {

    private Tokenizer tokenizer;

    @Before
    public void setup() {
        this.tokenizer = new Tokenizer("Hello ,.;World!");
    }

    @Test
    public void whenPassingUppercaseThenGettingLowerCase() {
        final List<String> listOfTokens = this.tokenizer.process();
        listOfTokens.forEach(token -> assertEquals(token, token.toLowerCase()));
    }

    @Test
    public void whenPassingWordsSeparatedByASpaceThenGetTwoTokens() {
        final List<String> listOfTokens = this.tokenizer.process();
        assertEquals(listOfTokens.size(), 2);
        assertEquals(listOfTokens.get(0), "Hello");
        assertEquals(listOfTokens.get(1), ",.;World!");
    }

    @Test
    public void whenPassingWordsSeparatedByAComaThenGetTwoTokens() {
        final List<String> listOfTokens = this.tokenizer.process();
        assertEquals(listOfTokens.size(), 2);
        assertEquals(listOfTokens.get(0), "Hello ");
        assertEquals(listOfTokens.get(1), ".;World!");
    }

    @Test
    public void whenPassingWordsSeparatedByADotThenGetTwoTokens() {
        final List<String> listOfTokens = this.tokenizer.process();
        assertEquals(listOfTokens.size(), 2);
        assertEquals(listOfTokens.get(0), "Hello ,");
        assertEquals(listOfTokens.get(1), ";World!");
    }

    @Test
    public void whenPassingWordsSeparatedByASemicolonThenGetTwoTokens() {
        final List<String> listOfTokens = this.tokenizer.process();
        assertEquals(listOfTokens.size(), 2);
        assertEquals(listOfTokens.get(0), "Hello ,.");
        assertEquals(listOfTokens.get(1), "World!");
    }
}
