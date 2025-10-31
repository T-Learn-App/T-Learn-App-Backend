package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectpractice.tlearnapp.repositories.entities.Word;

@Repository
public interface WordsRepository extends JpaRepository<Word, Long> {

    @Query(value = "SELECT * FROM words ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    public Word findRandomWord();
}
