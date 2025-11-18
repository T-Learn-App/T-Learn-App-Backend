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
}
