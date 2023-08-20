package com.ap.mindhub.homebanking.repositories;

import com.ap.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;


@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client,Long>{

    public List<Client> findByLastName(String lastName);

    public Client findByEmail(String email);

}
