package com.ap.mindhub.homebanking.services.implement;

import com.ap.mindhub.homebanking.models.Card;
import com.ap.mindhub.homebanking.repositories.CardRepository;
import com.ap.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
