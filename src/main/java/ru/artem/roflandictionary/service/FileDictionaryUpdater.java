package ru.artem.roflandictionary.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class FileDictionaryUpdater implements DictionaryUpdater<MultipartFile> {

    private final List<Dictionary> dictionaries;

    public FileDictionaryUpdater(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    @SneakyThrows //dont care about exceptions xD
    public void update(MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        List<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines().toList();
        inputStream.close();
        dictionaries.forEach(dictionary -> dictionary.updateDictionary(lines));
    }

    @Override
    public boolean isApplicable(MultipartFile data) {
        return true;
    }
}
