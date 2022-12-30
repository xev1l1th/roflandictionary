package ru.artem.roflandictionary.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DictionaryRepo {

    private final DictionaryParser parser;
    private final Random random;
    @Getter
    private boolean isConfigured = false;

    private volatile Map<String, List<String>> dictionary;

    public void updateDictionary(List<String> lines){
        this.isConfigured = true;
        this.dictionary = parser.parse(lines);
    }

    public DictionaryRepo(DictionaryParser parser){
        this.parser = parser;
        this.random = new Random();
    }

    public String getRandom(){
        List<String> strings = new ArrayList<>(dictionary.keySet());
        //Collections.shuffle(strings);
        return strings.get(random.nextInt(strings.size()));
    }

    public List<String> getDefinition(String key){
        return dictionary.get(key);
    }

}
