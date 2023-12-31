package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.models.*;
import com.ap.mindhub.homebanking.repositories.CardRepository;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import com.ap.mindhub.homebanking.services.CardService;
import com.ap.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
@RestController
public class CardController {

    @Autowired
    ClientService clientService;

    @Autowired
    CardService cardService;


    @RequestMapping(value = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());

        if (client.existCard(client.getCards(),cardColor,cardType)) {
            return new ResponseEntity<>("you already have a card " + cardColor + " " + cardType ,HttpStatus.FORBIDDEN);
        }
        Card newCard = new Card(client, cardType, cardColor);
        client.addCard(newCard);
        cardService.saveCard(newCard);
         return new ResponseEntity<>("you have created " + cardType + " " + cardColor ,HttpStatus.CREATED);
    }


}