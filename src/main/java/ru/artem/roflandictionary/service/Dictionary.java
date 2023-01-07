package ru.artem.roflandictionary.service;

import ru.artem.roflandictionary.data.Word;

import java.util.List;

public interface Dictionary {

    boolean isConfigured();

    List<String> getDefinition(String key);

    Word getRandom();

    void updateDictionary(List<String> lines);
}
