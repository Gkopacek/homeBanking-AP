package com.ap.mindhub.homebanking.repositories;

import com.ap.mindhub.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {

}
