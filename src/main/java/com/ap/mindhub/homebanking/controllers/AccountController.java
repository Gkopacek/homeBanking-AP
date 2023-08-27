package com.ap.mindhub.homebanking.controllers;


import com.ap.mindhub.homebanking.dtos.AccountDTO;
import com.ap.mindhub.homebanking.dtos.ClientDTO;
import com.ap.mindhub.homebanking.models.Account;
import com.ap.mindhub.homebanking.models.Client;
import com.ap.mindhub.homebanking.repositories.AccountRepository;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestMapping("/api")
@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){

        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    //

    @PostMapping("clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        if(authentication != null){
            Client client = clientRepository.findByEmail(authentication.getName());
            List<Account> accounts = new ArrayList<>();
            if(client.getAccounts().toArray().length<=2){
                Account newAccount = new Account(0);
                client.addAccount(newAccount);
                accountRepository.save(newAccount);
                clientRepository.save(client);

                return new ResponseEntity<>("Account created with success", HttpStatus.CREATED);

            }else{
                return new ResponseEntity<>("You only can have 3 accounts", HttpStatus.FORBIDDEN);
            }
        }
    return new ResponseEntity<>("No authenticated user in this Session", HttpStatus.FORBIDDEN);
    }

    @GetMapping("clients/current/accounts")
    public List<AccountDTO> getAuthClientAccounts(Authentication authentication){

        if(authentication != null){
            Client client = clientRepository.findByEmail(authentication.getName());
            return client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());

            }
        List<AccountDTO> empty = new LinkedList<>();
        return empty;
    }

    }

