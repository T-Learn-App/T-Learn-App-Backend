package projectpractice.tlearnapp.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.entities.StatQueue;
import projectpractice.tlearnapp.repositories.StatQueueRepository;

import java.time.Duration;

@Slf4j
@Component
public class DbCleanerJob implements JobTrigger {

    private final StatQueueRepository statQueueRepository;
    private final Long limit;

    public DbCleanerJob(StatQueueRepository statQueueRepository, @Value("${batch.size}") Long limit) {
        this.statQueueRepository = statQueueRepository;
        this.limit = limit;
    }

    @Override
    @Transactional
    public void run(Integer butchSize, Integer iterationSize, Duration before) {
        statQueueRepository.deleteByStatusOrState(StatQueue.Status.COMPLETED, limit);
    }
}