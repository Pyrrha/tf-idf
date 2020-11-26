package com.epita.tfidf.service.synonyms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SynonymReplacer {

    private final Map<String,String> dicoSyn;

    public Map<String, String> getDicoSyn() {
        return dicoSyn;
    }

    public SynonymReplacer() {
        dicoSyn = new HashMap<>();
        String synonymsFile = "src/main/resources/synonyms/en_thesaurus_min.json";
        List<String> list;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(synonymsFile))) {
            list = br.lines().collect(Collectors.toList());

            for (String line: list) {
                ObjectMapper objectMapper = new ObjectMapper();
                Word word = objectMapper.readValue(line, Word.class);
                List<String> listSynonyms = word.getData().getSynonyms();
                for (String synonym: listSynonyms) {
                    dicoSyn.put(synonym, word.getWord());
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> process(@NotNull final List<String> tokens) {
        List <String> changedToken = new ArrayList<>();
        for (String token: tokens) {
            if (this.dicoSyn.containsKey(token))
                changedToken.add(dicoSyn.get(token));
            else
                changedToken.add(token);
        }
        return changedToken;
    }

}
