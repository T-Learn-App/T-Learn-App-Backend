package projectpractice.tlearnapp.jobs;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.enums.StatQueueStatus;
import projectpractice.tlearnapp.repositories.StatQueueRepository;

@Slf4j
@Component
@AllArgsConstructor
public class DbCleanerJob {

    private final StatQueueRepository statQueueRepository;

    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void clean() {
        try {
            log.info("Starting database cleanup...");
            statQueueRepository.deleteByStatusOrState(StatQueueStatus.COMPLETED);
            log.info("Database cleanup completed");
        } catch (Exception e) {
            log.error("Error during database cleanup", e);
        }
    }
}