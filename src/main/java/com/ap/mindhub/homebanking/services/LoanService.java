package com.ap.mindhub.homebanking.services;

import com.ap.mindhub.homebanking.dtos.LoanDTO;
import com.ap.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getAllLoans();

    boolean loanExistById(Long id);

    Loan findById(Long id);
}
