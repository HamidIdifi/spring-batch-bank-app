package com.example.bankspringbatch.services;

import com.example.bankspringbatch.entities.BankTransaction;
import org.springframework.stereotype.Service;


public interface BankTransactionService {
    BankTransaction save(BankTransaction bankTransaction);
}
