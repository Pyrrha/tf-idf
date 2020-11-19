package com.epita.tfidf.service.tokenizer;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Token {
    private final String value;

    public Token(@NotNull String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return value.equals(token.value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
