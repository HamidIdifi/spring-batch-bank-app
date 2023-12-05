package com.example.bankspringbatch.services;

import com.example.bankspringbatch.dtos.TransactionCsvDTO;
import com.example.bankspringbatch.entities.Account;

public interface AccountService {
    Account saveAccount(TransactionCsvDTO dto);
}
