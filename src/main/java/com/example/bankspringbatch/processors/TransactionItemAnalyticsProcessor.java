package com.example.bankspringbatch.processors;

import com.example.bankspringbatch.dtos.TransactionCsvDTO;
import com.example.bankspringbatch.entities.BankTransaction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Slf4j
@Getter
public class TransactionItemAnalyticsProcessor implements ItemProcessor<TransactionCsvDTO, TransactionCsvDTO> {
    private double totalDebit;
    private double totalCredit;

    @Override
    public TransactionCsvDTO process(TransactionCsvDTO bankTransaction) throws Exception {
        if(bankTransaction.getTransactionType().equals("D")) totalDebit+= bankTransaction.getTransactionAmount();
        else if(bankTransaction.getTransactionType().equals("C")) totalCredit-= bankTransaction.getTransactionAmount();
        return bankTransaction;
    }
}
