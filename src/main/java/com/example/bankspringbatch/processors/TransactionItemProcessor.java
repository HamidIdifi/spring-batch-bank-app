package com.example.bankspringbatch.processors;

import com.example.bankspringbatch.entities.BankTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Slf4j
public class TransactionItemProcessor implements ItemProcessor<BankTransaction, BankTransaction> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        BankTransaction transformedBankTransaction = BankTransaction.builder()
                .id(bankTransaction.getId())
                .transactionType(bankTransaction.getTransactionType())
                .transactionDate(dateFormat.parse(bankTransaction.getStrTransactionDate()))
                .transactionAmount(bankTransaction.getTransactionAmount())
                .accountId(bankTransaction.getAccountId())
                .build();
        log.info("Converting (" + bankTransaction + ") into (" + transformedBankTransaction + ")");
        return transformedBankTransaction;
    }
}
