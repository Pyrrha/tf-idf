package com.epita.tfidf.service.vector;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TestVector {

    private Vector vector;

    @Before
    public void setup() {
        List<String> tokens = Arrays.asList("hello", "world");
        this.vector = new Vector(tokens);
    }

    @Test
    public void when_Then_() {
        //TODO
    }
}
