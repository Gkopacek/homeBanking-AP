package com.ap.mindhub.homebanking.services;

import com.ap.mindhub.homebanking.dtos.ClientDTO;
import com.ap.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getAllClients();

    Client findByEmail(String email);

    void SaveClient(Client client);

    ClientDTO findById(Long id);
}
