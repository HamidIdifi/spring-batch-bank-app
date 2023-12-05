package com.example.bankspringbatch.services.impl;

import com.example.bankspringbatch.dtos.TransactionCsvDTO;
import com.example.bankspringbatch.entities.Account;
import com.example.bankspringbatch.entities.BankTransaction;
import com.example.bankspringbatch.exceptions.ElementNotFoundException;
import com.example.bankspringbatch.mappers.BankTransactionMapper;
import com.example.bankspringbatch.repositories.AccountRepository;
import com.example.bankspringbatch.repositories.TransactionRepository;
import com.example.bankspringbatch.services.AccountService;
import com.example.bankspringbatch.services.BankTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BankTransactionMapper bankTransactionMapper;
    @Override
    public Account saveAccount(TransactionCsvDTO dto) {
        Optional<Account> account = accountRepository.findById(dto.getAccountId());
        BankTransaction bankTransaction = new BankTransaction();
        if(account.isPresent()){
            bankTransaction = bankTransactionMapper.fromCsv(dto);
            bankTransaction.setAccount(account.get());
           transactionRepository.save(bankTransaction);
            return accountRepository.save(account.get());
        }
        else {
            Account newAccount = new Account();
            newAccount.setId(dto.getAccountId());
            bankTransaction = bankTransactionMapper.fromCsv(dto);
            bankTransaction.setAccount(newAccount);
            Account account1 = accountRepository.save(newAccount);
            transactionRepository.save(bankTransaction);
            return account1;
        }

    }
}
