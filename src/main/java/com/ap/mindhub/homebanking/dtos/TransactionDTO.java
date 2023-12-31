package com.ap.mindhub.homebanking.dtos;

import com.ap.mindhub.homebanking.models.Account;
import com.ap.mindhub.homebanking.models.Transaction;
import com.ap.mindhub.homebanking.models.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;
    private Account account;
    private double amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;

    public TransactionDTO(){}

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.account = transaction.getaccount();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.type = transaction.getType();
    }

    public long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }
}
