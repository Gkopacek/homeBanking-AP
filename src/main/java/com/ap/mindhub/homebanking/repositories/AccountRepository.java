package com.ap.mindhub.homebanking.repositories;

import com.ap.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account,Long> {

}
