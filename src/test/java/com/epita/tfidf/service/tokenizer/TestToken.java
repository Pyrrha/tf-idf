package com.epita.tfidf.service.tokenizer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestToken {

    @Test
    public void whenCreateTokenThenGetValue() {
        String value = "value";
        Token token = new Token(value);
        assertEquals(value, token.getValue());
    }
}
