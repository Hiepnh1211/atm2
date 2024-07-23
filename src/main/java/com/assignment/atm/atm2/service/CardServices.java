package com.assignment.atm.atm2.service;

import com.assignment.atm.atm2.entity.Card;
import com.assignment.atm.atm2.repository.CardRepository;
import com.assignment.atm.atm2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class CardServices {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utilities utilities;
    Random random = new Random();
    public List<Card> listAll() {
        return cardRepository.findAll();
    }
    public Card findCardById(long id){
        return cardRepository.findById(Long.valueOf(String.valueOf(id))).get();
    }

    public void balanceUpdate(long cardId, double amount){
        Card card = cardRepository.findById(Long.valueOf(String.valueOf(cardId))).orElse(null);
        if (card != null){
            card.setBalance(card.getBalance() + amount);
            cardRepository.save(card);
        }
    }
    public Card spendingLimitUpdate(long cardId, double amount){
        Card card = cardRepository.findById(Long.valueOf(String.valueOf(cardId))).orElse(null);
        if (card != null){
            card.setSpendingLimit(amount);
            return cardRepository.save(card);
        }
        return null;
    }
    public long cardNumberGenerator(String type){
        long number = 100_000_000_000_000L + (long)(random.nextDouble() * 900_000_000_000_000L);;
        StringBuilder cardNumber = new StringBuilder();
        if (type.equals(Constants.DEBIT_CARD_TYPE)){
            cardNumber.append(3);
        } else if (type.equals(Constants.CREDIT_CARD_TYPE)) {
            cardNumber.append(4);
        }else {
            return 0;
        }
        cardNumber.append(number);
        while (cardRepository.findById(Long.parseLong(cardNumber.toString())).isPresent()){
            number = 100_000_000_000_000L + (long)(random.nextDouble() * 900_000_000_000_000L);;
            cardNumber = new StringBuilder();
            if (type.equals(Constants.DEBIT_CARD_TYPE)){
                cardNumber.append(3);
            } else {
                cardNumber.append(4);
            }
            cardNumber.append(number);
        }
        return Long.parseLong(cardNumber.toString());
    }

    public Card debitCardMaking(String cardHolderName){
        if (userRepository.existsById(cardHolderName)){
            long cardNumber = cardNumberGenerator(Constants.DEBIT_CARD_TYPE);
            String cvv = utilities.padLeftZeros(String.valueOf(random.nextInt(1000)), Constants.CVV_LENGTH);
            String cvvEncrypted = utilities.idGenerator(cardHolderName, cvv, "");
            cardRepository.debitCardMaking(cardNumber, cardHolderName, cvvEncrypted, LocalDate.now().plusYears(Constants.CARD_EXPIRATION_TIME));
            return new Card(cardNumber, cardHolderName, cvv, Constants.INITIAL_BALANCE, Constants.INITIAL_BALANCE, LocalDate.now(), LocalDate.now().plusYears(Constants.CARD_EXPIRATION_TIME),Constants.CARD_STATUS_ACTIVATED);
        }
        return null;
    }

    public Card creditCardMaking(String cardHolderName, double income){
        if (userRepository.existsById(cardHolderName)){
            long cardNumber = cardNumberGenerator(Constants.CREDIT_CARD_TYPE);
            String cvv = utilities.padLeftZeros(String.valueOf(random.nextInt(1000)), Constants.CVV_LENGTH);
            String cvvEncrypted = utilities.idGenerator(cardHolderName, cvv, "");
            cardRepository.debitCardMaking(cardNumber, cardHolderName, cvvEncrypted, LocalDate.now().plusYears(Constants.CARD_EXPIRATION_TIME));
            return new Card(cardNumber, cardHolderName, cvv, income, income, LocalDate.now(), LocalDate.now().plusYears(Constants.CARD_EXPIRATION_TIME),Constants.CARD_STATUS_ACTIVATED);
        }
        return null;
    }
}
