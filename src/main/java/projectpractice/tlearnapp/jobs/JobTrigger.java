package projectpractice.tlearnapp.jobs;

import java.time.Duration;

public interface JobTrigger {

    void run(Integer butchSize, Integer iterationSize, Duration before);
}
