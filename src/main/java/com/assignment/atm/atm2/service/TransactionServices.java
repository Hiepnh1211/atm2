package com.assignment.atm.atm2.service;

import com.assignment.atm.atm2.entity.Card;
import com.assignment.atm.atm2.entity.Transaction;
import com.assignment.atm.atm2.entity.User;
import com.assignment.atm.atm2.repository.CardRepository;
import com.assignment.atm.atm2.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServices {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private CardServices cardServices;
    @Autowired
    private Utilities utilities;

    public Transaction getTransactionById(String id){
        return transactionRepository.findById(Integer.valueOf(id)).get();
    }
    public Transaction transactionMaking(long cardId, String type, double amount, double balance){
        LocalDateTime transactionTime = LocalDateTime.now();
        String transactionId = utilities.idGenerator(String.valueOf(cardId), transactionTime.toString(), type);
        transactionRepository.transactionMaking(transactionId, cardId, type, transactionTime, amount, balance);
        return transactionRepository.findByTransactionId(transactionId);
    }
    public Transaction depositForDebit(long cardId, double amount){
        User user = userServices.getUserByCardNumber(cardId);
        userServices.updateBalance(user.getUserName(), amount, Constants.TRANSACTION_TYPE_DEPOSIT);
        return transactionMaking(cardId, Constants.TRANSACTION_TYPE_DEPOSIT, amount, user.getBalance());
    }
    public Transaction depositForCredit(long cardId, double amount){
        Card card = cardRepository.findById(cardId).orElseThrow();
        cardServices.balanceUpdate(cardId, amount);
        return transactionMaking(cardId, Constants.TRANSACTION_TYPE_DEPOSIT, amount, card.getBalance());
    }
    public Transaction withdraw(long cardId, double amount){
        User user = userServices.getUserByCardNumber(cardId);
        userServices.updateBalance(user.getUserName(), amount, Constants.TRANSACTION_TYPE_WITHDRAW);
        return transactionMaking(cardId, Constants.TRANSACTION_TYPE_WITHDRAW, amount, user.getBalance());
    }
    public Transaction transfer(long senderCardNumber, long receiverCardNumber, double amount){
        User sender = userServices.getUserByCardNumber(senderCardNumber);
        User receiver = userServices.getUserByCardNumber(receiverCardNumber);
        userServices.updateBalance(sender.getUserName(),amount,Constants.FUNCTION_SEND);
        userServices.updateBalance(receiver.getUserName(), amount, Constants.FUNCTION_RECEIVE);
        return transactionMaking(senderCardNumber, Constants.FUNCTION_SEND, amount, sender.getBalance());
    }
    public List<Transaction> getTransactionRecord(long id){
        List<Transaction> transactionList = new ArrayList<>();
        List<String> idList = transactionRepository.getTransactionRecord(id);
        System.out.println(idList.get(0));
        for (String transactionId: idList){
            transactionList.add(transactionRepository.findByTransactionId(transactionId));
        }
        return transactionList;
    }
}
