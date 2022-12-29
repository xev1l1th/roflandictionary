package ru.artem.roflandictionary.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class DictionaryRepo {

    private final FileReader fileReader;
    private final DictionaryParser parser;
    private final Random random;
    private final String path;

    private volatile Map<String, List<String>> dictionary;

    @PostConstruct
    @SneakyThrows
    private void init(){
        List<String> strings = fileReader.readLines(path);
        this.dictionary = parser.parse(strings);
    }

    public DictionaryRepo(FileReader fileReader, DictionaryParser parser, @Value("${dictionary.path}") String path){
        this.fileReader = fileReader;
        this.parser = parser;
        this.path = path;
        this.random = new Random();
    }

    public String getRandom(){
        List<String> strings = new ArrayList<>(dictionary.keySet());
        Collections.shuffle(strings);
        return strings.get(random.nextInt(strings.size()));
    }

    public List<String> getDefinition(String key){
        return dictionary.get(key);
    }

}
