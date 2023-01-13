package ru.artem.roflandictionary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.artem.roflandictionary.data.Word;
import ru.artem.roflandictionary.repo.WordRepo;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class DbWordService implements WordService {

    private final WordRepo wordRepo;

    @Autowired
    private ApplicationContext applicationContext;

    private DbWordService wordService;
    private final Random random;

    public DbWordService(WordRepo wordRepo, Random random) {
        this.wordRepo = wordRepo;
        this.random = random;
    }

    @PostConstruct
    public void init() {
        wordService = applicationContext.getBean(DbWordService.class);
    }

    @Cacheable("words")
    public List<Word> getAllWords() {
        return wordRepo.findAll();
    }

    public Word getWord(String value) {
        return wordRepo.findById(value).orElseThrow(() -> new RuntimeException(String.format("no such word %s", value)));
    }

    //return id
    public String saveWord(Word word) {
        Word wordInDb = wordRepo
                .save(word);
        return wordInDb.getValue();
    }

    @Transactional
    public void saveWords(List<Word> words) {
        if (CollectionUtils.isEmpty(words)) {
            log.info("list<words> is empty");
            return;
        }
        words.forEach(this::saveWord);
    }

    @Override
    public Word findRandomWord() {
        List<Word> allWords = wordService.getAllWords();
        return allWords.get(random.nextInt(allWords.size()));
    }
}
