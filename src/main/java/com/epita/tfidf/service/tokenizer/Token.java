package com.epita.tfidf.service.tokenizer;

import org.jetbrains.annotations.NotNull;

/**
 * Class Token
 */
public class Token {
    private final String value;

    /**
     * Constructor of Token class
     * @param value String a word representing a value
     */
    public Token(@NotNull String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Function equals
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return value.equals(token.value);
    }

    /**
     * Function to display more easily the token value
     * @return String The word contained into the token
     */
    @Override
    public String toString() {
        return getValue();
    }
}
