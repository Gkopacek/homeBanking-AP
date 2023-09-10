package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.ap.mindhub.homebanking.dtos.LoanDTO;
import com.ap.mindhub.homebanking.models.*;
import com.ap.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    ClientService clientService;

    @Autowired
    LoanService loanService;

    @Autowired
    AccountService accountService;

    @Autowired
    ClientLoanService clientLoanService;

    @Autowired
    TransactionService transactionService;


    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getAllLoans();
    }


    @RequestMapping(value = "/loans", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Object> aplyLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        if(authentication != null){
            System.out.println(authentication);
            Client client = clientService.findByEmail(authentication.getName());

            if(loanApplicationDTO.getAmount()<=0){
                return new ResponseEntity<>("the amount need to be higher to 0", HttpStatus.FORBIDDEN);
            }

            if(loanApplicationDTO.getPayments()<=0){
                return new ResponseEntity<>("the payments need to be higher to 0", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.getToAccountNumber().isEmpty()|| loanApplicationDTO.getToAccountNumber() == null) {
                return new ResponseEntity<>("toAcountNumber value is empty", HttpStatus.FORBIDDEN);
            }

            if(!loanService.loanExistById(loanApplicationDTO.getLoanId())){
                return new ResponseEntity<>("the requested loan id doesn´t exist", HttpStatus.FORBIDDEN);
            }

            Loan loan = loanService.findById(loanApplicationDTO.getLoanId());

            if( loanApplicationDTO.getAmount() > loan.getMaxAmount()){
                return new ResponseEntity<>("the requested amount exceeds the maximum amount allowed for this loan", HttpStatus.FORBIDDEN);
            }

            List<Integer> loanPayments = loan.getPayments();
                     boolean noExistPayment = true;
            for (Integer payment: loanPayments) {
                if(payment==loanApplicationDTO.getPayments()){
                    System.out.println(payment);
                     noExistPayment = false;
                }

            }if(noExistPayment){
                return new ResponseEntity<>("The requested Payment does not exist", HttpStatus.FORBIDDEN);
            }

            if(accountService.findByNumber(loanApplicationDTO.getToAccountNumber()) == null){
                return new ResponseEntity<>("The destination account don´t exist", HttpStatus.FORBIDDEN);
            }

            Set<Account> accounts = client.getAccounts();
            boolean noOwnedAccount = true;
            for (Account account : accounts) {

                if (account.getNumber().equals(loanApplicationDTO.getToAccountNumber())) {
                    noOwnedAccount = false;
                }

            }
            if(noOwnedAccount){
                return new ResponseEntity<>("you don´t own this account", HttpStatus.FORBIDDEN);
            }

            Account loanerAccount =  accountService.findByNumber(loanApplicationDTO.getToAccountNumber());
            ClientLoan newLoan =  new ClientLoan(loanApplicationDTO.getAmount()+loanApplicationDTO.getAmount()*0.2, loanApplicationDTO.getPayments(), client, loan);


            client.addClientLoan(newLoan);
            clientLoanService.saveClientLoan(newLoan);

            Transaction loanTransaction = new Transaction(loanApplicationDTO.getAmount(), loan.getName() + "-loan approved" , LocalDateTime.now(), TransactionType.CREDIT);
            loanerAccount.addTransaction(loanTransaction);
            transactionService.saveTransaction(loanTransaction);
            loanerAccount.setBalance(loanerAccount.getBalance()+loanApplicationDTO.getAmount());
            accountService.saveAccount(loanerAccount);

            String loanType = loanService.findById(loanApplicationDTO.getLoanId()).getName();

            return new ResponseEntity<>("Loan " + loanType + " of " + loanApplicationDTO.getAmount() + " and " + loanApplicationDTO.getPayments() + " payments was Created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("No authenticated user in this Session", HttpStatus.FORBIDDEN);
    }


}

