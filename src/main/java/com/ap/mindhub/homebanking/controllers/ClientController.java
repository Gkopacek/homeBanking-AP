package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.models.Client;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    //Definir un método público que retorne List<Client> (puedes llamarlo getClients)
    //

    @RequestMapping("/clients")
    public List<Client> getClients(){
        return clientRepository.findAll();
    }


}
