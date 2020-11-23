package com.epita.tfidf.service.vector;

import com.epita.tfidf.service.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.RequiredArgsConstructor;

public class Vector {

    private Map<String, VectorValues> vector;

    public Vector(List<Token> tokens) {
        this.vector = new HashMap<>();
    }

    public Map<String, VectorValues> vectorize(final List<String> tokens) {
        int total = tokens.size();
        int index = 0;

        for (String token : tokens) {
            if (this.vector.containsKey(token)) {
                this.vector.get(token).positions.add(index);
                this.vector.get(token).frequency += 1/total;
            }
            else {
                this.vector.put(token, new VectorValues(1/total, new ArrayList<>(index)));
            }
            ++index;
        }

        return this.vector;
    }

    @RequiredArgsConstructor
    @Data
    class VectorValues {
        private int frequency;
        private List<Integer> positions;

        public VectorValues(int frequency, List<Integer> positions) {
            this.frequency = frequency;
            this.positions = positions;
        }
    }

}
