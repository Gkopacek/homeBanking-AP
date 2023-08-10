package com.ap.mindhub.homebanking.repositories;

import com.ap.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {



}
