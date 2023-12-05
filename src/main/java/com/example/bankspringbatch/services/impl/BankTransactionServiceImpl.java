package com.example.bankspringbatch.services.impl;

import com.example.bankspringbatch.entities.BankTransaction;
import com.example.bankspringbatch.repositories.AccountRepository;
import com.example.bankspringbatch.repositories.TransactionRepository;
import com.example.bankspringbatch.services.BankTransactionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor @NoArgsConstructor
public class BankTransactionServiceImpl implements BankTransactionService {
    private TransactionRepository transactionRepository;
    @Override
    public BankTransaction save(BankTransaction bankTransaction) {
        return transactionRepository.save(bankTransaction);
    }
}
