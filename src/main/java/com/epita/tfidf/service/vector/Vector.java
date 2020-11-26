package com.epita.tfidf.service.vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Class Vector
 */
public class Vector {

    private final List<String> tokens;

    public Vector(@NotNull List<String> tokens) {
        this.tokens = tokens;
    }

    /**
     * Create a Map containing each distinct token, associated with an object
     * representing its occurence frequency and its positions in the list.
     * @return vector
     */
    public Map<String, VectorValues> vectorize() {
        Map<String, VectorValues> vector = new HashMap<>();
        int total = this.tokens.size();
        int index = 0;

        for (String token : this.tokens) {
            if (vector.containsKey(token)) {
                vector.get(token).positions.add(index);
                vector.get(token).frequency += 1/total;
            }
            else {
                vector.put(token, new VectorValues(1/total, new ArrayList<>(index)));
            }
            ++index;
        }

        return vector;
    }

    /**
     * Object representing the frequency and the positions of a token.
     */
    @Data
    public static class VectorValues {
        private int frequency;
        private List<Integer> positions;

        public VectorValues(int frequency, List<Integer> positions) {
            this.frequency = frequency;
            this.positions = positions;
        }
    }
}
