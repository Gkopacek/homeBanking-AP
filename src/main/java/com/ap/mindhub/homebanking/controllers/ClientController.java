package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.dtos.ClientDTO;
import com.ap.mindhub.homebanking.models.Client;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    //Definir un método público que retorne List<Client> (puedes llamarlo getClients)
    //

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }


}
