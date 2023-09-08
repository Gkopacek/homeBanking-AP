package com.ap.mindhub.homebanking.services.implement;

import com.ap.mindhub.homebanking.dtos.LoanDTO;
import com.ap.mindhub.homebanking.models.Loan;
import com.ap.mindhub.homebanking.repositories.LoanRepository;
import com.ap.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getAllLoans() {
       return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

    @Override
    public boolean loanExistById(Long id) {
        return loanRepository.existsById(id);
    }

    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }
}
