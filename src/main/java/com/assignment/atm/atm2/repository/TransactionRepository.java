package com.assignment.atm.atm2.repository;

import com.assignment.atm.atm2.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO transaction_info(transaction_id, card_number, transaction_type, transaction_date, amount, balance) VALUES (:transactionId,:cardId,:type,:transactionDate,:amount,:balance)")
    void transactionMaking(@Param("transactionId") String transactionId, @Param("cardId") long cardId, @Param("type") String type, @Param("transactionDate") LocalDateTime transactionDate, @Param("amount") double amount, @Param("balance") double balance);

    @Query(nativeQuery = true, value = "SELECT COUNT(transaction_info.transaction_id) FROM transaction_info WHERE DATE(transaction_date) = (SELECT CURRENT_DATE()) AND card_number = :cardNumber AND transaction_type = :type")
    int checkTransactionLimit(@Param("cardNumber") long cardNumber, @Param("type") String type);

    @Query(value = "SELECT transaction_id FROM transaction_info WHERE card_number = :cardNumber ORDER BY transaction_date DESC LIMIT 5",nativeQuery = true)
    List<String> getTransactionRecord(@Param("cardNumber") long cardNumber);

    Transaction findByTransactionId(String transactionId);
}
