package com.ap.mindhub.homebanking.services;


import com.ap.mindhub.homebanking.models.Card;
import org.springframework.http.ResponseEntity;

public interface CardService {
    void saveCard(Card card);

    Card findCardById(Long id);


}