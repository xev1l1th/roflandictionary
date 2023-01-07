package ru.artem.roflandictionary.service;

import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.data.Word;

import java.util.List;

@Service
public class StoredDictionary implements Dictionary{

    private final WordService wordService;
    private final DictionaryParser<List<String>, List<Word>> parser;

    public StoredDictionary(WordService wordService, DictionaryParser<List<String>, List<Word>> parser) {
        this.wordService = wordService;
        this.parser = parser;
    }

    @Override
    //make sure we already have data in db, anyway we can add it
    public boolean isConfigured() {
        return true;
    }

    @Override
    public List<String> getDefinition(String key) {
        Word word = wordService.getWord(key);
        return word.getDefinition();
    }

    @Override
    public Word getRandom() {
        return wordService.findRandomWord();
    }

    @Override
    public void updateDictionary(List<String> lines) {
        List<Word> parse = parser.parse(lines);
        wordService.saveWords(parse);
    }
}
