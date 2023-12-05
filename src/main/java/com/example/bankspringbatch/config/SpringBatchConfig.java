package com.example.bankspringbatch.config;

import com.example.bankspringbatch.dtos.TransactionCsvDTO;
import com.example.bankspringbatch.processors.TransactionItemAnalyticsProcessor;
import com.example.bankspringbatch.processors.TransactionItemProcessor;
import com.example.bankspringbatch.services.AccountService;
import com.example.bankspringbatch.utils.CustomCompositeItemReader;
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
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
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
    AccountService accountService;
    @Autowired
    JobCompletionNotificationListener listener;

    @Bean
    public FlatFileItemReader<TransactionCsvDTO> reader() {
        return new FlatFileItemReaderBuilder<TransactionCsvDTO>()
                .name("transactionItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("data.csv"))
                .delimited()
                .names("transactionId", "accountId", "strTransactionDate", "transactionType", "transactionAmount")
                .targetType(TransactionCsvDTO.class)
                .build();
    }

    @Bean
    public StaxEventItemReader<TransactionCsvDTO> xmlReader() {
        return new StaxEventItemReaderBuilder<TransactionCsvDTO>()
                .name("transactionXmlReader")
                .resource(new ClassPathResource("data.xml"))
                .addFragmentRootElements("transaction")
                .unmarshaller(transactionXmlUnmarshaller())
                .build();
    }

    @Bean
    public Jaxb2Marshaller transactionXmlUnmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(TransactionCsvDTO.class);
        return marshaller;
    }

    @Bean
    ItemWriter<TransactionCsvDTO> writer() {
        return (Chunk<? extends TransactionCsvDTO> chunk) -> {
            chunk.forEach(tr -> accountService.saveAccount(tr));
        };
    }



    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<TransactionCsvDTO, TransactionCsvDTO>chunk(100, transactionManager)
                .reader(reader())
                .processor(compositeItemProcessor())
                .writer(writer())
                .build();
    }
    @Bean
    public CustomCompositeItemReader<TransactionCsvDTO> customCompositeItemReader() {
        CustomCompositeItemReader<TransactionCsvDTO> compositeReader = new CustomCompositeItemReader<>();

        compositeReader.setItemReaders(Arrays.asList(reader(), xmlReader()));

        return compositeReader;
    }

    @Bean
    public ItemProcessor<? super TransactionCsvDTO, ? extends TransactionCsvDTO> compositeItemProcessor() {
        List<ItemProcessor<TransactionCsvDTO, TransactionCsvDTO>> itemProcessorList = Arrays.asList(transactionItemProcessor(), transactionItemAnalyticsProcessor());
        CompositeItemProcessor<TransactionCsvDTO, TransactionCsvDTO> compositeItemProcessor = new CompositeItemProcessor<>();
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
