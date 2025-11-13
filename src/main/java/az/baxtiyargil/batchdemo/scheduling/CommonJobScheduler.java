package az.baxtiyargil.batchdemo.scheduling;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;

@Component
public class CommonJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job productJob;

    public CommonJobScheduler(JobLauncher jobLauncher, Job productJob) {
        this.jobLauncher = jobLauncher;
        this.productJob = productJob;
    }

    @Scheduled(fixedRate = 30000)
    public void runJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addLong("productId", 13L)
                .toJobParameters();
        System.out.println("Starting job...");
        var start = Instant.now();
        jobLauncher.run(productJob, params);
        var elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("Done job... Elapsed: " + elapsed);
    }
}
