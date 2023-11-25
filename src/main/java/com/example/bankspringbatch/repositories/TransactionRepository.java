package com.example.bankspringbatch.repositories;

import com.example.bankspringbatch.entities.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<BankTransaction,Long> {
}
