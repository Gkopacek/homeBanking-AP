package com.ap.mindhub.homebanking.services.implement;

import com.ap.mindhub.homebanking.models.Transaction;
import com.ap.mindhub.homebanking.repositories.TransactionRepository;
import com.ap.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
