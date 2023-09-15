package com.ap.mindhub.homebanking.controllers;

import com.ap.mindhub.homebanking.models.*;
import com.ap.mindhub.homebanking.services.CardService;
import com.ap.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RequestMapping("/api")
@RestController
public class CardController {

    @Autowired
    ClientService clientService;

    @Autowired
    CardService cardService;


    @PostMapping(value = "/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());


        if (client.existCard(client.getCards(),cardColor,cardType)) {
            Set<Card> cards= new HashSet<>();
            cards = client.getCards();
            for (Card card: cards
                 ) {
                if(card.isActive()==false){
                    card.setActive(true);
                    cardService.saveCard(card);
                    return new ResponseEntity<>("you have created " + cardType + " " + cardColor ,HttpStatus.CREATED);
                }
            }

            return new ResponseEntity<>("you already have a card " + cardColor + " " + cardType ,HttpStatus.FORBIDDEN);
        }
        Card newCard = new Card(client, cardType, cardColor);
        client.addCard(newCard);
        cardService.saveCard(newCard);
         return new ResponseEntity<>("you have created " + cardType + " " + cardColor ,HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id ,Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Set<Card> cards = new HashSet<>();
        cards = client.getCards();
        for (Card card: cards) {
            if(card.getId()==id){
                card.setActive(false);
                cardService.saveCard(card);
                return new ResponseEntity<>("Card was erased with success", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("id isn´t correct", HttpStatus.FORBIDDEN);
            }
        }
       return new ResponseEntity<>("No allowed", HttpStatus.FORBIDDEN);
    }

}