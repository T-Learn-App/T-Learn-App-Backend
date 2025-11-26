package projectpractice.tlearnapp.jobs;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.entities.Stat;
import projectpractice.tlearnapp.entities.StatQueue;
import projectpractice.tlearnapp.enums.StatsStatus;
import projectpractice.tlearnapp.repositories.StatQueueRepository;
import projectpractice.tlearnapp.repositories.StatsRepository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
public class StatQueueJob implements JobTrigger {

    private StatQueueRepository statQueueRepository;
    private StatsRepository statsRepository;

    @Override
    @Transactional
    public void run(Integer butchSize, Integer iterationSize, Duration before) {
        List<StatQueue> lockedReports =
                statQueueRepository.findByStatusLocked(butchSize, StatQueue.Status.ACCEPTED);
        for (StatQueue stat : lockedReports) {
            Long userId = stat.getUser().getId();
            Long wordId = stat.getWord().getId();
            Optional<Stat> statOptional =
                    statsRepository.findByUserIdAndWordId(userId, wordId);
            if (statOptional.isEmpty()) {
                statsRepository.save(new Stat(stat.getUser(), stat.getWord(), 0L, StatsStatus.DRAFT));
            } else {
                statsRepository.updateAttemptsAndStatusByUserIdAndWordId(
                        userId, wordId, StatsStatus.getStatus(statOptional.get().getAttempts()));
            }
        }
    }
}
