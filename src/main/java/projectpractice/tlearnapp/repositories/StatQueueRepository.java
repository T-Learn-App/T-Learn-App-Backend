package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectpractice.tlearnapp.entities.StatQueue;

import java.util.List;

public interface StatQueueRepository extends JpaRepository<StatQueue, Long> {

    @Query(value = "SELECT * FROM stat_queue " +
            "WHERE status = #status AND state = #state " +
            "FOR UPDATE SKIP LOCKED LIMIT #butchSize",
            nativeQuery = true)
    List<StatQueue> findByStatusLocked(Integer butchSize, String status, String state);
}
