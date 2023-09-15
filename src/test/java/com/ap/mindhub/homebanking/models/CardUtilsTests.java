package com.ap.mindhub.homebanking.models;

import com.ap.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



@SpringBootTest
public class CardUtilsTests {
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }

    @Test
    public void cardNumberNotStartWithMiddleDash(){
        String cardNumber = CardUtils.getCardNumber();

        assertThat(cardNumber, not(startsWith("-")));
    }

    @Test
    public void cvvNumberIsNotEqualsToZero(){
        int cvvNumber = CardUtils.getCvv();
        assertThat(cvvNumber, is(not(0)));
    }

    @Test
    public void cvvNumberIsEqualOrLessTo999(){
        int cvvNumber = CardUtils.getCvv();
        assertThat(cvvNumber, lessThanOrEqualTo(999));
    }
}
