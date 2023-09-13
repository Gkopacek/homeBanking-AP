package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.dtos.ClientDTO;
import com.ap.mindhub.homebanking.models.Account;
import com.ap.mindhub.homebanking.models.Client;
import com.ap.mindhub.homebanking.repositories.AccountRepository;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import com.ap.mindhub.homebanking.services.AccountService;
import com.ap.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequestMapping("/api")
@RestController
public class ClientController {

    @Autowired
    public ClientService clientService;

    @Autowired
    public AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //Definir un método público que retorne List<Client> (puedes llamarlo getClients)
    //

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getAllClients();
    }


    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {



        if (firstName.isEmpty()) {

            return new ResponseEntity<>("first name is required", HttpStatus.FORBIDDEN);

        }

        if (lastName.isEmpty()) {

            return new ResponseEntity<>("last name is required", HttpStatus.FORBIDDEN);

        }

        if (email.isEmpty()) {

            return new ResponseEntity<>("email is required", HttpStatus.FORBIDDEN);

        }

        if (password.isEmpty()) {

            return new ResponseEntity<>("password is required", HttpStatus.FORBIDDEN);

        }


        if (clientService.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        clientService.SaveClient(newClient);
        Account newAccount = new Account(0);
        newClient.addAccount(newAccount);
        accountService.saveAccount(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }




    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.findById(id);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getAuthClient(Authentication authentication){
        if(authentication != null){
            Client client = clientService.findByEmail(authentication.getName());
                ClientDTO clientDTO = new ClientDTO(client);
                return clientDTO;
        }

        Client clientNull = new Client("null","null","null","null");
        return new ClientDTO(clientNull);
    }


}
