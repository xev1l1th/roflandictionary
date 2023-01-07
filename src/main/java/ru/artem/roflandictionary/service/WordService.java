package ru.artem.roflandictionary.service;

import ru.artem.roflandictionary.data.Word;

import java.util.List;

public interface WordService {
    List<Word> getAllWords();

    Word getWord(String value);

    String saveWord(Word word);

    void saveWords(List<Word> words);

    Word findRandomWord();
}
