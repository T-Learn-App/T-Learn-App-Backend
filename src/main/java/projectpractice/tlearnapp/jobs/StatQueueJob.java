package projectpractice.tlearnapp.jobs;

import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class StatQueueJob implements JobTrigger {

    @Override
    public void run(Integer butchSize, Integer iterationSize, Duration before) {

    }
}
