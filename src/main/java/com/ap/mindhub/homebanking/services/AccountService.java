package com.ap.mindhub.homebanking.services;


import com.ap.mindhub.homebanking.dtos.AccountDTO;
import com.ap.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    //accountRepository.save(newAccount);

    List<AccountDTO> getAllAccounts();

    AccountDTO findById(Long id);

    void saveAccount(Account account);

}
