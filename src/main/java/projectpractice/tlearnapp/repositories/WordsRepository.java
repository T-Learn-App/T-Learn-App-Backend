package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectpractice.tlearnapp.entities.Word;

import java.util.Optional;

public interface WordsRepository extends JpaRepository<Word, Long> {

    @Query(value = "SELECT * FROM words ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Word> findRandomWord();
}
