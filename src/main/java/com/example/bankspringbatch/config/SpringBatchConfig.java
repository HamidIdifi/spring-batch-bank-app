package com.example.bankspringbatch.config;

import com.example.bankspringbatch.entities.BankTransaction;
import com.example.bankspringbatch.processors.TransactionItemAnalyticsProcessor;
import com.example.bankspringbatch.processors.TransactionItemProcessor;
import com.example.bankspringbatch.repositories.TransactionRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringBatchConfig {
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    JobCompletionNotificationListener listener;

    @Bean
    public FlatFileItemReader<BankTransaction> reader() {
        return new FlatFileItemReaderBuilder<BankTransaction>()
                .name("transactionItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("data.csv"))
                .delimited()
                .names("id", "accountId", "strTransactionDate", "transactionType", "transactionAmount")
                .targetType(BankTransaction.class)
                .build();
    }

    @Bean
    ItemWriter<BankTransaction> writer() {
        return (Chunk<? extends BankTransaction> chunk) ->
                transactionRepository.saveAll(chunk);
    }


    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<BankTransaction, BankTransaction>chunk(100, transactionManager)
                .reader(reader())
                .processor(compositeItemProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<? super BankTransaction, ? extends BankTransaction> compositeItemProcessor() {
        List<ItemProcessor<BankTransaction, BankTransaction>> itemProcessorList = Arrays.asList(transactionItemProcessor(), transactionItemAnalyticsProcessor());
        CompositeItemProcessor<BankTransaction, BankTransaction> compositeItemProcessor = new CompositeItemProcessor<>();
        compositeItemProcessor.setDelegates(itemProcessorList);
        return compositeItemProcessor;

    }

    @Bean
    TransactionItemProcessor transactionItemProcessor() {
        return new TransactionItemProcessor();
    }

    @Bean
    TransactionItemAnalyticsProcessor transactionItemAnalyticsProcessor() {
        return new TransactionItemAnalyticsProcessor();
    }

    @Bean
    public Job importUserJob() {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1())
                .build();
    }

}
