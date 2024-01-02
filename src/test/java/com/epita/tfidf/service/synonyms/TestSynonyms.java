package com.epita.tfidf.service.synonyms;

import org.junit.Test;
import java.util.Map;

public class TestSynonyms {

    private SynonymReplacer synonymReplacer = new SynonymReplacer();

    @Test
    public void testingJsonMapping() {
        for (Map.Entry<String,String> map: synonymReplacer.getDicoSyn().entrySet()) {
            System.out.println("key: "+map.getKey()+" || value: "+map.getValue());
        }
    }
}
