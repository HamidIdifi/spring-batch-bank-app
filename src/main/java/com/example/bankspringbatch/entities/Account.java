package com.example.bankspringbatch.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    private Long id;
    private float solde;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<BankTransaction> bankTransactionList = new ArrayList<>();
    public void addTransaction(BankTransaction bankTransaction){
        bankTransactionList.add(bankTransaction);
    }
}
