package com.assignment.atm.atm2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "card_info")
public class Card {
    @Id
    @Column(name = "card_number", unique = true)
    private long cardNumber;
    @Column(name = "card_holder_name")
    private String cardHolderName;
    @Column(name = "cvv")
    private String cvv;
    @Column(name = "spending_limit")
    private double spendingLimit;
    @Column(name = "balance")
    private double balance;
    @Column(name = "creation_date")
    private LocalDate creationDate = LocalDate.now();
    @Column(name = "expiration_date")
    private LocalDate expirationDate = LocalDate.now().plusYears(3);
    @Column(name = "card_status")
    private String cardStatus;
    public Card(){}

    public Card(long cardNumber, String cardHolderName, String cvv, double spendingLimit, double balance, LocalDate creationDate, LocalDate expirationDate, String cardStatus) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.spendingLimit = spendingLimit;
        this.balance = balance;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.cardStatus = cardStatus;
    }
    @OneToMany(targetEntity = Transaction.class)
    private List<Transaction> transactionList;
}
