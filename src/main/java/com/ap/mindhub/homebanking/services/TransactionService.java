package com.ap.mindhub.homebanking.services;

import com.ap.mindhub.homebanking.models.Transaction;
import org.springframework.stereotype.Service;


public interface TransactionService {
    void saveTransaction(Transaction transaction);
}
