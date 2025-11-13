package projectpractice.tlearnapp.jobs;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import static projectpractice.tlearnapp.common.utils.StringWorker.toCamelCase;

@Component
@AllArgsConstructor
@Slf4j
public class JobWrapper {

    private final TaskScheduler scheduler;
    private final ApplicationContext context;
    private final JobConfiguration configuration;
    private final ConcurrentHashMap<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        var beans = context.getBeansOfType(JobTrigger.class);
        log.info("Job enabled: {}", beans.keySet());
        configuration.forEach((key, value) -> {
            if (value.getEnabled()) {
                schedule(key);
            }
        });
    }

    private void schedule(String jobName) {
        JobConfiguration.JobProperty cron;
        try {
            cron = configuration.get(jobName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Job not found with name: " + jobName, e);
        }

        var job = context.getBean(toCamelCase(jobName), StatQueueJob.class);
        var future = scheduler.schedule(() -> {
            log.info("{} started...", jobName);
            try {
                job.run(cron.getBatchSize(), cron.getIterationSize(), null);
                log.info("{} finished...", jobName);
            } catch (Exception e) {
                log.error("{} error...", jobName, e);
            }
        }, new CronTrigger(cron.getCron()));

        tasks.put(jobName, Objects.requireNonNull(future));
    }
}
