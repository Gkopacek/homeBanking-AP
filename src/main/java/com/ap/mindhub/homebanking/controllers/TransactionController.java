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

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Object> createDebitTransaction(
            Authentication authentication, @RequestParam Double debitAmount,
            @RequestParam String transactionDescription, @RequestParam String originAccount,
            @RequestParam String destinationAccount) {

        if (authentication != null) {
            Client client = clientRepository.findByEmail(authentication.getName());

            if (debitAmount == null) {
                return new ResponseEntity<>("Amount is empty", HttpStatus.FORBIDDEN);
            } else if (debitAmount <= 0) {
                return new ResponseEntity<>("Amount can´t be 0 or less", HttpStatus.FORBIDDEN);
            }

            if (transactionDescription.isEmpty()) {
                return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
            }

            if (originAccount.isEmpty()) {
                return new ResponseEntity<>("Origin account is empty", HttpStatus.FORBIDDEN);
            }

            if (destinationAccount.isEmpty()) {
                return new ResponseEntity<>("Destination account is empty", HttpStatus.FORBIDDEN);
            }

            if (originAccount.equals(destinationAccount)) {
                return new ResponseEntity<>("Origin account and destination account are the same", HttpStatus.FORBIDDEN);
            }

            if(accountRepository.findByNumber(originAccount) == null){
                return new ResponseEntity<>("The origin account don´t exist", HttpStatus.FORBIDDEN);
            }

            if(accountRepository.findByNumber(destinationAccount) == null){
                return new ResponseEntity<>("The destination account don´t exist", HttpStatus.FORBIDDEN);
            }

            Set<Account> accounts = client.getAccounts();
            boolean noOwnedAccount = true;
            for (Account account : accounts) {

                if (account.getNumber().equals(originAccount)) {
                    noOwnedAccount = false;
                    if (account.getBalance() < debitAmount){
                        return new ResponseEntity<>("You don´t have enough money to make this transaction", HttpStatus.FORBIDDEN);
                    }

                }

            }
            if(noOwnedAccount){
                return new ResponseEntity<>("you don´t own this account", HttpStatus.FORBIDDEN);
            }


            Account origin = accountRepository.findByNumber(originAccount);
            Account destination = accountRepository.findByNumber(destinationAccount);



            Transaction originTransaction = new Transaction(-debitAmount, transactionDescription + origin.getNumber(), LocalDateTime.now(), TransactionType.DEBIT);
            origin.addTransaction(originTransaction);
            transactionRepository.save(originTransaction);
            origin.setBalance(origin.getBalance()-debitAmount);
            accountRepository.save(origin);

            Transaction destinationTransaction = new Transaction(debitAmount, transactionDescription + destination.getNumber(), LocalDateTime.now(), TransactionType.CREDIT);
            destination.addTransaction(destinationTransaction);
            transactionRepository.save(destinationTransaction);
            destination.setBalance(destination.getBalance()+debitAmount);
            accountRepository.save(destination);


            return new ResponseEntity<>("Created", HttpStatus.CREATED);

        }

        return new ResponseEntity<>("No authenticated user in this Session", HttpStatus.FORBIDDEN);

    }



}




