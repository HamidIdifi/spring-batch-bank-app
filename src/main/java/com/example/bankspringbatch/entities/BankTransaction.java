package com.example.bankspringbatch.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor @NoArgsConstructor @ToString @Builder
public class BankTransaction {
    @Id
    private Long id;
    private Date transactionDate;
    private String transactionType;
    private double transactionAmount;
    @ManyToOne
    private Account account;
}
