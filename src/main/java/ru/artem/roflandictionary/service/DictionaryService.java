package ru.artem.roflandictionary.service;

import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.data.Word;

import java.util.List;

@Service
public class DictionaryService {

    private final Dictionary storedDictionary;

    public DictionaryService(Dictionary storedDictionary) {
        this.storedDictionary = storedDictionary;
    }

    public boolean compareDefinition(String key, String value) {
        List<String> s = storedDictionary.getDefinition(key);
        if (s == null || s.isEmpty()) {
            System.out.printf("no such word %s in dictionary(wtf??)", key);
            return false;
        }
        return compareListOfDef(value, s);
    }

    private boolean compareListOfDef(String s1, List<String> def) {
        return def.stream().anyMatch(s -> compareStrings(s, s1));
    }

    private boolean compareStrings(String s1, String s2) {
        return s1 != null && s1.equalsIgnoreCase(s2);
    }

    public Word getRandomWord() {
        return storedDictionary.getRandom();
    }

    public List<String> getDefinition(String key) {
        return storedDictionary.getDefinition(key);
    }
}
