package ru.artem.roflandictionary.service;

import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.data.Word;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StringToWordDictionaryParser implements DictionaryParser<List<String>, List<Word>> {

    @SuppressWarnings("unchecked")
    @Override
    public List<Word> parse(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return lines
                .stream()
                //ignore notices
                .filter(line -> !line.startsWith("//"))
                .map(this::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    //TODO add interface Modifier with "String modify(String)" method and use it like composite to get a lot of explanation
    private String excludeExplanation(String s) {
        if (s.contains("(")) {
            int i = s.indexOf("(");
            return s.substring(0, i);
        }
        return s;
    }

    //TODO mb use stack to get explanation in explanation (info (info about info))
    private String extractExplanation(String s) {
        if (s.contains("(") && s.contains(")")) {
            int i = s.indexOf("(");
            int j = s.indexOf(")");
            return s.substring(i, j);
        }
        //return empty if explanation is not present
        return "";
    }

    private Word parse(String line) {
        //a word can have multiple definitions, you can choose one of the given
        Word word = new Word();
        List<String> meaning;
        //string determination rules
        String[] split = line.split(" - ");
        if (split.length != 2) {
            return null;
        }
        String definition = split[1];
        if (definition != null && !definition.isEmpty()) {
            //check double definition divided by '/'
            if (definition.contains("/")) {
                meaning = List.of(definition.split("/"));
            } else {
                meaning = List.of(definition);
            }

            //exclude explanation from definition
            List<String> def = meaning.stream()
                    .map(this::excludeExplanation)
                    .toList();

            String value = split[0];

            //exclude explanation from value;
            //String modified = excludeExplanation(value);

            //get all extra from value and definition
            String extra = Stream.concat(meaning.stream(), Stream.of(/*add explanations for value in the future*/))
                    .map(this::extractExplanation)
                    .collect(Collectors.joining(" - "));

            word.setValue(value);
            word.setDefinition(def);
            word.setExtra(extra);
        }
        return word;
    }

}
