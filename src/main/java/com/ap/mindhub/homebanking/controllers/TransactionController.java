package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.models.*;
import com.ap.mindhub.homebanking.repositories.AccountRepository;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import com.ap.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@RequestMapping("/api")
@RestController
public class TransactionController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Object> createDebitTransaction(
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
            @RequestParam Double amount, @RequestParam String description,
            Authentication authentication) {

        if (authentication != null) {
            Client client = clientRepository.findByEmail(authentication.getName());

            if (amount == null) {
                return new ResponseEntity<>("Amount is empty", HttpStatus.FORBIDDEN);
            } else if (amount <= 0) {
                return new ResponseEntity<>("Amount can´t be 0 or less", HttpStatus.FORBIDDEN);
            }

            if (description.isEmpty()) {
                return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
            }

            if (fromAccountNumber.isEmpty()) {
                return new ResponseEntity<>("Origin account is empty", HttpStatus.FORBIDDEN);
            }

            if (toAccountNumber.isEmpty()) {
                return new ResponseEntity<>("Destination account is empty", HttpStatus.FORBIDDEN);
            }

            if (fromAccountNumber.equals(toAccountNumber)) {
                return new ResponseEntity<>("Origin account and destination account are the same", HttpStatus.FORBIDDEN);
            }

            if(accountRepository.findByNumber(fromAccountNumber) == null){
                return new ResponseEntity<>("The origin account don´t exist", HttpStatus.FORBIDDEN);
            }

            if(accountRepository.findByNumber(toAccountNumber) == null){
                return new ResponseEntity<>("The destination account don´t exist", HttpStatus.FORBIDDEN);
            }

            Set<Account> accounts = client.getAccounts();
            boolean noOwnedAccount = true;
            for (Account account : accounts) {

                if (account.getNumber().equals(fromAccountNumber)) {
                    noOwnedAccount = false;
                    if (account.getBalance() < amount){
                        return new ResponseEntity<>("You don´t have enough money to make this transaction", HttpStatus.FORBIDDEN);
                    }

                }

            }
            if(noOwnedAccount){
                return new ResponseEntity<>("you don´t own this account", HttpStatus.FORBIDDEN);
            }


            Account origin = accountRepository.findByNumber(fromAccountNumber);
            Account destination = accountRepository.findByNumber(toAccountNumber);



            Transaction originTransaction = new Transaction(-amount, description + origin.getNumber(), LocalDateTime.now(), TransactionType.DEBIT);
            origin.addTransaction(originTransaction);
            transactionRepository.save(originTransaction);
            origin.setBalance(origin.getBalance()-amount);
            accountRepository.save(origin);

            Transaction destinationTransaction = new Transaction(amount, description + destination.getNumber(), LocalDateTime.now(), TransactionType.CREDIT);
            destination.addTransaction(destinationTransaction);
            transactionRepository.save(destinationTransaction);
            destination.setBalance(destination.getBalance()+amount);
            accountRepository.save(destination);


            return new ResponseEntity<>("Transaction created", HttpStatus.CREATED);

        }

        return new ResponseEntity<>("No authenticated user in this Session", HttpStatus.FORBIDDEN);

    }



}




