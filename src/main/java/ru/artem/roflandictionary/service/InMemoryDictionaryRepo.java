package ru.artem.roflandictionary.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.data.Word;

import java.util.List;
import java.util.Random;

@Service
public class InMemoryDictionaryRepo implements Dictionary {

    private final DictionaryParser<List<String>, List<Word>> parser;
    private final Random random;
    @Getter
    private boolean isConfigured = false;

    private volatile List<Word> listWord;

    @Override
    public void updateDictionary(List<String> lines) {
        this.isConfigured = true;
        this.listWord = parser.parse(lines);
    }

    public InMemoryDictionaryRepo(DictionaryParser<List<String>, List<Word>> parser) {
        this.parser = parser;
        this.random = new Random();
    }

    @Override
    public Word getRandom() {
        return listWord.get(random.nextInt(listWord.size()));
    }

    @Override
    public List<String> getDefinition(String key) {
        for (Word word : listWord) {
            if (word.getValue().equals(key)) {
                return word.getDefinition().stream().toList();
            }
        }
        throw new RuntimeException("no such word exception");
    }

}
