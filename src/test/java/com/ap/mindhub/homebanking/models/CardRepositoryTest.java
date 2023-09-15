package com.ap.mindhub.homebanking.models;

import com.ap.mindhub.homebanking.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardRepositoryTest {

    @Autowired
    CardRepository cardRepository;

    @Test
    public void existCards(){

        List<Card> accounts = cardRepository.findAll();

        assertThat(accounts,is(not(empty())));

    }

    @Test

    public void existCardColorGold(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards, hasItem(hasProperty("color", is(CardColor.GOLD))));
    }


    /*
*     @Test

    public void existCardColorGold(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards, hasItem(hasProperty("color", is("Personal"))));
}
*  @Test

    public void balanceIsPositive(){

        List<Account> Accounts = accountRepository.findAll();

        assertThat(Accounts, hasItem(hasProperty("balance", not(lessThan(0)))));

}
*
*     @Test

    public void existCardColorGold(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards, hasItem(hasProperty("color", is(CardColor.GOLD))));
    }
*
* */

}