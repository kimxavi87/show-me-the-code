package com.kimxavi87.springbatch.stepbystep;

import com.kimxavi87.springbatch.stepbystep.step.ListItemReader;
import com.kimxavi87.springbatch.stepbystep.step.SomethingItemProcessor;
import com.kimxavi87.springbatch.stepbystep.step.SomethingItemWriter;
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
public class BatchStepByStepConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final String STEPBYSTEP_JOBNAME = "stepbystep-job";

    @Bean
    public Job stepByStepJob() {
        return jobBuilderFactory.get(STEPBYSTEP_JOBNAME)
                .incrementer(new RunIdIncrementer())
                .start(stepByStep())
                .build();
    }

    @Bean
    public Step stepByStep() {
        return stepBuilderFactory.get("step-by-step")
                .<String, String> chunk(10)
                .reader(new ListItemReader())
                .processor(new SomethingItemProcessor())
                .writer(new SomethingItemWriter())
                .build();
    }
}
