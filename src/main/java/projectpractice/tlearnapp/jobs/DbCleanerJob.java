package projectpractice.tlearnapp.jobs;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.enums.StatQueueStatus;
import projectpractice.tlearnapp.repositories.StatQueueRepository;

import java.time.Duration;

@Slf4j
@Component
@AllArgsConstructor
public class DbCleanerJob implements JobTrigger {

    private final StatQueueRepository statQueueRepository;

    @Override
    @Transactional
    public void run(Integer butchSize, Integer iterationSize, Duration before) {
        Long limit = 30L;
        statQueueRepository.deleteByStatusOrState(StatQueueStatus.COMPLETED, limit);
    }
}