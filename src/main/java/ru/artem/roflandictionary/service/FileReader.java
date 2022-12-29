package ru.artem.roflandictionary.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class FileReader {

    public List<String> readLines(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }
}
