package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.dtos.AccountDTO;
import com.ap.mindhub.homebanking.models.*;
import com.ap.mindhub.homebanking.repositories.CardRepository;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

@RequestMapping("/api")
@RestController
public class CardController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;


    @RequestMapping(value = "/api/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType type, @RequestParam CardColor color, Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());
            if(!(client.getCards().contains(color))&&(client.getCards().contains(type))){
                Card newCard = new Card();
                client.addCard(newCard);
                cardRepository.save(newCard);
                clientRepository.save(client);

                return new ResponseEntity<>("Gold Card created with success", HttpStatus.CREATED);

            } else{
            return new ResponseEntity<>("You already have a card of this type", HttpStatus.CREATED);
        }



    }

    @RequestMapping("/test")
    public ResponseEntity<Object> createCad(@RequestParam CardType type, @RequestParam CardColor color, Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());
        System.out.println(client.getCards());

            Card newCard = new Card();
            client.addCard(newCard);
            cardRepository.save(newCard);
            clientRepository.save(client);

            return new ResponseEntity<>("Gold Card created with success", HttpStatus.CREATED);



    }

}