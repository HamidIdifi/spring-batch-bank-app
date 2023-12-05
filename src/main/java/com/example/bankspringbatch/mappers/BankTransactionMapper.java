package com.example.bankspringbatch.mappers;

import com.example.bankspringbatch.dtos.TransactionCsvDTO;
import com.example.bankspringbatch.entities.Account;
import com.example.bankspringbatch.entities.BankTransaction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class BankTransactionMapper {
    public BankTransaction fromCsv(TransactionCsvDTO dto){
        return BankTransaction.builder()
                .id(dto.getTransactionId())
                .transactionAmount(dto.getTransactionAmount())
                .transactionType(dto.getTransactionType())
                .transactionAmount(dto.getTransactionAmount())
                .build();
    }
}
