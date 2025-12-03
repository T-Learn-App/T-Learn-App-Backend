package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectpractice.tlearnapp.entities.Word;

import java.util.List;

public interface WordsRepository extends JpaRepository<Word, Long> {

    @Query(value = "SELECT * FROM words ORDER BY RANDOM() LIMIT 10", nativeQuery = true)
    List<Word> findRandomWords();

    @Query(value = "SELECT * FROM words WHERE category_id = :categoryId LIMIT 10", nativeQuery = true)
    List<Word> findByCategoryId(@Param("categoryId") Long categoryId);
}
