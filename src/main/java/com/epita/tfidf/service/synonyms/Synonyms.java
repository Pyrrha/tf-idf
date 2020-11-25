package com.epita.tfidf.service.synonyms;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Synonyms {

    private Map<String,String> dicoSyn;

    public Synonyms(){

        dicoSyn = new HashMap();
        String fileName = "../../../resources/synonyms/en_thesaurus.json";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
/*
            for (String line : ):
                if (nb < 3):
                    continue;
                word = steam(line.get("word"));
                for (String synonym : line.get("data").get("synonyms")):
                dicoSyn.put(steam(synonym), word);

*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
