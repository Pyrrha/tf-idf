package com.epita.tfidf.service.vector;

import org.graalvm.compiler.lir.LIRInstruction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class TestVector {

    private Vector vector;

    @Before
    public void setup() {
        List<String> tokens = Arrays.asList("hello", "world");
        this.vector = new Vector(tokens);
    }

    @Test
    public void whenThen() {}
}
