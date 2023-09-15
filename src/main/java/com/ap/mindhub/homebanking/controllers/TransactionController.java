package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.models.*;
import com.ap.mindhub.homebanking.repositories.AccountRepository;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import com.ap.mindhub.homebanking.repositories.TransactionRepository;
import com.ap.mindhub.homebanking.services.AccountService;
import com.ap.mindhub.homebanking.services.ClientService;
import com.ap.mindhub.homebanking.services.TransactionService;
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
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/transactions")
    @Transactional
    public ResponseEntity<Object> createDebitTransaction(
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
            @RequestParam Double amount, @RequestParam String description,
            Authentication authentication) {

        if (authentication != null) {
            Client client = clientService.findByEmail(authentication.getName());

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

            if(accountService.findByNumber(fromAccountNumber) == null){
                return new ResponseEntity<>("The origin account don´t exist", HttpStatus.FORBIDDEN);
            }

            if(accountService.findByNumber(toAccountNumber) == null){
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


            Account origin = accountService.findByNumber(fromAccountNumber);
            Account destination = accountService.findByNumber(toAccountNumber);



            Transaction originTransaction = new Transaction(-amount, description + origin.getNumber(), LocalDateTime.now(), TransactionType.DEBIT);
            origin.addTransaction(originTransaction);
            transactionService.saveTransaction(originTransaction);
            origin.setBalance(origin.getBalance()-amount);
            accountService.saveAccount(origin);

            Transaction destinationTransaction = new Transaction(amount, description + destination.getNumber(), LocalDateTime.now(), TransactionType.CREDIT);
            destination.addTransaction(destinationTransaction);
            transactionService.saveTransaction(destinationTransaction);
            destination.setBalance(destination.getBalance()+amount);
            accountService.saveAccount(destination);


            return new ResponseEntity<>("Transaction created", HttpStatus.CREATED);

        }

        return new ResponseEntity<>("No authenticated user in this Session", HttpStatus.FORBIDDEN);

    }



}




