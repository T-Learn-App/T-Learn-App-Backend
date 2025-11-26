package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectpractice.tlearnapp.entities.Stat;
import projectpractice.tlearnapp.enums.StatsStatus;

import java.util.List;
import java.util.Optional;

public interface StatsRepository extends JpaRepository<Stat, Long> {

    @Query(value = "SELECT * FROM stats WHERE user_id = :userId", nativeQuery = true)
    List<Stat> findAllByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM stats WHERE user_id = :userId AND word_id = :wordId", nativeQuery = true)
    Optional<Stat> findByUserIdAndWordId(@Param("userId") Long userId, @Param("wordId") Long wordId);

    @Modifying
    @Query(value = """ 
            UPDATE stats SET attempts = attempts + 1, status = :status, updated_at = NOW()
            WHERE user_id = :userId AND word_id = :wordId
            """, nativeQuery = true)
    void updateAttemptsAndStatusByUserIdAndWordId(@Param("userId") Long userId,
                                                  @Param("wordId") Long wordId,
                                                  @Param("status") StatsStatus status);

    @Modifying
    @Query(value = """
                   WITH rotation_words AS (
                       DELETE FROM stats WHERE id IN (
                       SELECT * FROM stats s WHERE user_id = :userId AND attempts >= 3 AND updated_at > NOW() - 7
                       LIMIT 2)
                       RETURNING *
                   )
                   SELECT * FROM rotation_words
                   """, nativeQuery = true)
    List<Stat> findRotationStatsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
            WITH rotation_words_by_category AS (
                DELETE FROM stats WHERE id IN (
                SELECT * FROM stats s
                JOIN words w ON s.word_id = w.id
                JOIN categories c ON w.category_id = c.id
                WHERE s.user_id = :userId AND s.attempts >= 3 AND s.updated_at > NOW() - 7 AND c.name = :category
                LIMIT 2)
                RETURNING *
            )
            SELECT * FROM rotation_words_by_category
            """, nativeQuery = true)
    List<Stat> findRotationStatsByCategory(@Param("userId") Long userId, @Param("category") String category);
}
