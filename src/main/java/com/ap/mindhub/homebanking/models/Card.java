package com.ap.mindhub.homebanking.models;

import com.ap.mindhub.homebanking.utils.CardUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;



@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private  String cardHolder;
    private CardType type;
    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    private CardColor color;

    public  Card(){}

    public Card(Client client , CardType type, String number, int cvv, LocalDate thruDate, LocalDate fromDate, CardColor color) {
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.type = type;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.color = color;
        this.active = true;
    }

    public Card(Client client , CardType type, CardColor color) {
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.type = type;
        this.number = CardUtils.getCardNumber();
        this.cvv = CardUtils.getCvv();
        this.thruDate = LocalDate.now().plusYears(5);
        this.fromDate = LocalDate.now();
        this.color = color;
        this.active = true;
    }



    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}


