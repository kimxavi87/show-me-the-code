package com.kimxavi87.springbatch.tasklet;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BatchTaskletConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final String TASKLET_EXAMPLE_JOBNAME = "tasklet-job";

    @Bean
    public Job taskletJob() {
        return jobBuilderFactory.get(TASKLET_EXAMPLE_JOBNAME)
                .incrementer(new RunIdIncrementer())
                .start(testTasklet())
                .build();
    }

    @Bean
    public Step testTasklet() {
        return stepBuilderFactory.get("test-tasklet")
                .tasklet(new TestTasklet())
                .build();
    }

}
