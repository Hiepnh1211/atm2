package com.assignment.atm.atm2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@Table(name = "user_info")
public class User {
    @Id
    @Column(name = "user_name", unique = true)
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "contact_number")
    private String contactNumber;
    @Column(name = "gender")
    private String gender;
    @Column(name = "address")
    private String address;
    @Column(name = "balance")
    private double balance;
    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(String userName, String password, String name, String contactNumber, String gender, String address, double balance, String role) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.contactNumber = contactNumber;
        this.gender = gender;
        this.address = address;
        this.balance = balance;
        this.role = role;
    }

    @OneToMany(targetEntity = Card.class)
    private List<Card> cardList;
}
