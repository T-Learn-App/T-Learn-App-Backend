package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectpractice.tlearnapp.entities.StatQueue;
import projectpractice.tlearnapp.enums.StatQueueState;
import projectpractice.tlearnapp.enums.StatQueueStatus;

import java.util.List;

public interface StatQueueRepository extends JpaRepository<StatQueue, Long> {

    @Query(value = "SELECT * FROM stat_queue " +
            "WHERE status = #status AND state = #state " +
            "FOR UPDATE SKIP LOCKED LIMIT #butchSize",
            nativeQuery = true)
    List<StatQueue> findByStatusLocked(Integer butchSize, StatQueueStatus status, StatQueueState state);

    @Modifying
    @Query(value = "DELETE FROM stat_queue WHERE status = :status OR state = :state", nativeQuery = true)
    void deleteByStatusOrState(@Param("status") StatQueueStatus status, @Param("state") StatQueueState state);
}
