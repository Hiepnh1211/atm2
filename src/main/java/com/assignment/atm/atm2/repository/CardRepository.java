package com.assignment.atm.atm2.repository;

import com.assignment.atm.atm2.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "SELECT card_number FROM card_info WHERE card_number LIKE '3%' AND card_holder_name = :name", nativeQuery = true)
    List<Long> getDebitCardList(@Param("name") String name);
    @Query(value = "SELECT card_number FROM card_info WHERE card_number LIKE '4%' AND card_holder_name = :name", nativeQuery = true)
    List<Long> getCreditCardList(@Param("name") String name);
    @Query(value = "SELECT user_name FROM user_info WHERE user_name = (SELECT card_info.card_holder_name FROM card_info WHERE card_number = :id)",nativeQuery = true)
    String getUserByCardNumber(@Param("id") long id);
    @Modifying
    @Query(value = "INSERT INTO card_info (atm2.card_info.card_number, atm2.card_info.card_holder_name, atm2.card_info.cvv, atm2.card_info.expiration_date) VALUES (:cardNumber, :cardHolderName, :cvv, :expirationDate)", nativeQuery = true)
    void debitCardMaking(@Param("cardNumber") long cardNumber, @Param("cardHolderName") String cardHolderName, @Param("cvv") String cvv, @Param("expirationDate")LocalDate expirationDate);
    @Modifying
    @Query(value = "INSERT INTO card_info(card_number, card_holder_name, cvv, expiration_date, spending_limit, balance) VALUES (:cardNumber, :cardHolderName, :cvv, :expirationDate, :spendingLimit, :balance)", nativeQuery = true)
    void creditCardMaking(@Param("cardNumber") long cardNumber, @Param("cardHolderName") String cardHolderName, @Param("cvv") String cvv, @Param("expirationDate")LocalDate expirationDate, @Param("spendingLimit") double spendingLimit, @Param("balance") double balance);
}
