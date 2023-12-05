package com.example.bankspringbatch.repositories;

import com.example.bankspringbatch.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
