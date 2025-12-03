package projectpractice.tlearnapp.jobs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import projectpractice.tlearnapp.jobs.JobConfiguration.JobProperty;

import java.util.LinkedHashMap;

@Component
@ConfigurationProperties(prefix = "jobs")
public class JobConfiguration extends LinkedHashMap<String, JobProperty> {
    @Data
    public static class JobProperty {
        private Boolean enabled;
        private String cron;
        private Integer batchSize;
        private Integer iterationSize;
    }
}
