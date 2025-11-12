package az.baxtiyargil.batchdemo.configuration;

import az.baxtiyargil.batchdemo.batch.OrderItemProcessor;
import az.baxtiyargil.batchdemo.batch.OrderItemReader;
import az.baxtiyargil.batchdemo.batch.OrderItemWriter;
import az.baxtiyargil.batchdemo.domain.OrderItem;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {

    @Bean
    public Job orderItemJob(JobRepository jobRepository, Step orderItemStep) {
        return new JobBuilder("orderItemJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(orderItemStep)
                .build();
    }

    @Bean
    public Step orderItemStep(JobRepository jobRepository,
                              PlatformTransactionManager txManager,
                              OrderItemReader reader,
                              OrderItemProcessor processor,
                              OrderItemWriter writer) {
        return new StepBuilder("orderItemStep", jobRepository)
                .<OrderItem, OrderItem>chunk(10, txManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
