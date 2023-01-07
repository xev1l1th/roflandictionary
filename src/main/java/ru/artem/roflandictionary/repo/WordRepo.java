package ru.artem.roflandictionary.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artem.roflandictionary.data.Word;

@Repository
public interface WordRepo extends JpaRepository<Word, String> {
}
