package com.ap.mindhub.homebanking.repositories;

import com.ap.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository  extends JpaRepository<Account,Long> {

}
