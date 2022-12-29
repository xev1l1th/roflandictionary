package ru.artem.roflandictionary.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;

@Service
public class DictionaryParser {

    @SuppressWarnings("unchecked")
    public Map<String, List<String>> parse(List<String> lines){
        if(lines == null || lines.isEmpty()){
            return Collections.EMPTY_MAP;
        }
        Map<String, List<String>> map = lines
                .stream()
                //ignore notices
                .filter(line -> !line.startsWith("//"))
                .map(this::parse)
                .map(Map::entrySet)
                //should contain only one pair of elements
                .filter(e -> !e.isEmpty())
                .map(e -> e.stream().findFirst().get())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new ConcurrentHashMap<>(map);
    }

    private String excludeExplanation(String s){
        if (s.contains("(")) {
            int i = s.indexOf("(");
            return s.substring(0, i);
        }
        return s;
    }

    private Map<String, List<String>> parse(String line){
        //a word can have multiple definitions, you can choose one of the given
        Map<String, List<String>> res = new HashMap<>();
        List<String> meaning;
        //string determination rules
        String[] split = line.split(" - ");
        if(split.length != 2) {
            return res;
        }
        String definition = split[1];
        if(definition!= null && !definition.isEmpty()){
            //check double definition divided by '/'
            if(definition.contains("/")){
                meaning = List.of(definition.split("/"));
            } else {
                meaning = List.of(definition);
            }
            List<String> r = meaning.stream()
                    .map(this::excludeExplanation)
                    .toList();
            res.put(split[0], r);
        }
        return res;
    }

}
