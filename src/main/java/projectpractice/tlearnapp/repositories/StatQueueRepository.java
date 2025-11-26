package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectpractice.tlearnapp.entities.StatQueue;

import java.util.List;

public interface StatQueueRepository extends JpaRepository<StatQueue, Long> {

    @Query(value = """ 
            SELECT * FROM stat_queue
            WHERE status = :status FOR UPDATE SKIP LOCKED LIMIT #butchSize
            """,
            nativeQuery = true)
    List<StatQueue> findByStatusLocked(Integer butchSize, StatQueue.Status status);

    @Modifying
    @Query(value = """
        WITH cte_limit AS (
        SELECT id FROM stat_queue
        WHERE status = :status AND created_at > NOW() - INTERVAL '1 day'
        LIMIT :limit
        )
        DELETE FROM stat_queue
        WHERE id IN (SELECT id FROM cte_limit);
        """, nativeQuery = true)
    void deleteByStatusOrState(@Param("status") StatQueue.Status status, @Param("limit") Long limit);
}
