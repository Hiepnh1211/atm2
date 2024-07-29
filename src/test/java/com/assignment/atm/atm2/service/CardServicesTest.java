package com.assignment.atm.atm2.service;

import com.assignment.atm.atm2.entity.Card;
import com.assignment.atm.atm2.entity.Transaction;
import com.assignment.atm.atm2.repository.CardRepository;
import com.assignment.atm.atm2.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.SpringVersion;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class CardServicesTest {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardServices cardServices;
    @Autowired
    private TransactionServices transactionServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private Utilities utilities;
    @Test
    void getCardList() {
//        Long list = cardRepository.findById(3123456789102365L).get().getCardNumber();
//        System.out.println(list);
        Transaction transaction = transactionServices.depositForDebit(3123456789102365L, 20);
        if (transaction != null){
            System.out.println(transaction.getId());
        }
    }

    @Test
    void findCardById() {
        System.out.println(cardServices.findCardById(3123456789102365L).getCardStatus());
        System.out.println(cardRepository.findAll());
    }

    @Test
    void findTransaction(){
        Transaction transaction = transactionRepository.findByTransactionId("f4cafe0fd87669a01d9b2929935df8a89737541ae9c0cb0aaa3fad3e6675dde8");
        if (transaction != null){
            System.out.println(transaction.getCardNumber());
        }else {
            System.out.println("Failed");
        }
    }
    @Test
    void getCardList2() {
//        Long list = cardRepository.findById(3123456789102365L).get().getCardNumber();
//        System.out.println(list);
        Transaction transaction = transactionServices.withdraw(3123456789102365L, 20);
        if (transaction != null){
            System.out.println(transaction.getId());
        }
    }

    @Test
    void test() {
//        Long list = cardRepository.findById(3123456789102365L).get().getCardNumber();
//        System.out.println(list);
        Transaction transaction = transactionServices.transfer(3123456789102365L,3123456789102365L, 20);
        System.out.println(transaction.getTransactionId());

    }

    @Test
    void test2() {
        System.out.println( cardServices.cardNumberGenerator(Constants.DEBIT_CARD_TYPE));
    }

    @Test
    void test3() {
        System.out.println(userServices.userNameGenerator("Nguyen Van Ba"));
    }
    @Test
    void test4(){
//        System.out.println(userServices.login("NguyenVanBinh1", "8606"));
        System.out.println(userServices.getUserByName("NguyenVanBinh1").getCardList().get(0).getCardNumber());
    }
    @Test
    void test5() {
        System.out.println(cardServices.findCardById(3123456789102365L).getTransactionList());
    }
    @Test
    void test6() {
        //System.out.println(cardServices.getCardList(userServices.getUserByName("NguyenVanBinh1").getCardList(), Constants.DEBIT_CARD_TYPE));
        cardServices.creditCardMaking("NguyenVanBinh1", 2000);
    }
}