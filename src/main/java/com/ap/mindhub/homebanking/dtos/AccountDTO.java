package com.ap.mindhub.homebanking.dtos;

import com.ap.mindhub.homebanking.models.Account;
import com.ap.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDate date;
    private double balance;

    private Set<Transaction> transactions = new HashSet<>();

    public AccountDTO(){}

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.date = account.getDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBalance() {
        return balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }
}
