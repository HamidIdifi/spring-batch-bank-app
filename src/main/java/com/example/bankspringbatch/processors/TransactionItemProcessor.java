package com.example.bankspringbatch.processors;

import com.example.bankspringbatch.dtos.TransactionCsvDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TransactionItemProcessor implements ItemProcessor<TransactionCsvDTO, TransactionCsvDTO> {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public TransactionCsvDTO process(TransactionCsvDTO item) throws Exception {
        //item.transactionDate(LocalDateTime.parse(item.strTransactionDate(), dateFormat));
        return item;
    }
}
