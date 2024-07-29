package com.assignment.atm.atm2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transaction_info")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "card_number", insertable=false, updatable=false)
    private long cardNumber;
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate = LocalDateTime.now();
    @Column(name = "amount")
    private double amount;
    @Column(name = "balance")
    private double balance;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(referencedColumnName = "card_number", name = "card_number", nullable = false)
    private Card card;
    public Transaction(){

    }
    public Transaction(String transactionId, long cardNumber, String transactionType, LocalDateTime transactionDate, double amount, double balance) {
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.balance = balance;
    }
}
