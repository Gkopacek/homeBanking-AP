package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.dtos.ClientDTO;
import com.ap.mindhub.homebanking.models.Client;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    public ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //Definir un método público que retorne List<Client> (puedes llamarlo getClients)
    //

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
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


        if (clientRepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }



        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }




    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getAuthClient(Authentication authentication){
        if(authentication != null){
            Client client = clientRepository.findByEmail(authentication.getName());
                ClientDTO clientDTO = new ClientDTO(client);
                return clientDTO;
        }

        Client clientNull = new Client("null","null","null","null");
        return new ClientDTO(clientNull);
    }


}
