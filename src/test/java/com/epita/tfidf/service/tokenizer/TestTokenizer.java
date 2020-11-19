package com.epita.tfidf.service.tokenizer;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestTokenizer {

    @Test
    public void whenPassingUppercaseThenGettingLowerCase() {
        final List<Token> listOfTokens = Tokenizer.process("Hello World!");

        for (Token token : listOfTokens) {
            System.out.println(token);
            assertEquals(token.getValue(), token.getValue().toLowerCase());
        }
    }

    @Test
    public void whenPassingWordsSeparatedByASpaceThenGetTwoTokens() {
        final List<Token> listOfTokens = Tokenizer.process("Hello World");
        assertEquals(2, listOfTokens.size());
    }
}
