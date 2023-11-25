package com.example.bankspringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("!!! JOB FINISHED! Time to verify the results");
    }
}
