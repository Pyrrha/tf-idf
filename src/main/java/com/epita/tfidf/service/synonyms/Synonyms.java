package com.epita.tfidf.service.synonyms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class Synonyms {

    private final Map<String,String> dicoSyn;

    public Synonyms() {
        dicoSyn = new HashMap<>();
        File synonymsFile = new File("src/main/resources/synonyms/en_thesaurus.json");
        BufferedReader reader;

//        try (FileReader fileReader = new FileReader(synonymsFile)) {
//            reader = new BufferedReader(fileReader);
//            String line;
//            while ((line = reader.readLine()) != null) {
//                list.add(line);
//            }
//            reader.close();
//        }
//        catch (IOException e) {
//            log.error(e.getMessage());
//        }

        try (List<String> stream = Files.lines(Paths.get(fileName)).collect(Collectors.toList())) {
            for (String line : stream.collect(Collectors.toList())) {
                if (nb < 3) continue;
                word = stream(line.get("word"));
                for (String synonym : line.get("data").get("synonyms"))
                    dicoSyn.put(stream(synonym), word);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> process(@NotNull List<String> tokens) {
        return tokens;
    }

}
