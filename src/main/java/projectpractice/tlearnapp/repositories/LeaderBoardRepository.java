package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectpractice.tlearnapp.entities.LeaderBoard;

import java.util.Optional;

public interface LeaderBoardRepository extends JpaRepository<LeaderBoard, Long> {

    @Query(value = """
        SELECT * FROM leaderboard
        WHERE user_id = :userId
        """,
        nativeQuery = true)
    Optional<LeaderBoard> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
        UPDATE leaderboard
        SET total_score = total_score + :score
        WHERE user_id = :userId
        """,
        nativeQuery = true)
    void updateLeaderBoardTotalScoreByUserId(@Param("userId") Long userId, @Param("score") Long score);
}
