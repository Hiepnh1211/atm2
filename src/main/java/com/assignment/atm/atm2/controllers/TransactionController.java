package com.assignment.atm.atm2.controllers;

import com.assignment.atm.atm2.entity.Card;
import com.assignment.atm.atm2.entity.Transaction;
import com.assignment.atm.atm2.repository.TransactionRepository;
import com.assignment.atm.atm2.service.CardServices;
import com.assignment.atm.atm2.service.Constants;
import com.assignment.atm.atm2.service.TransactionServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TransactionController {
    @Autowired private TransactionServices transactionServices;
    @Autowired private CardServices cardServices;
    @Autowired private TransactionRepository transactionRepository;
    @GetMapping("/deposit_form")
    public String depositForm(HttpSession session){
//        Card debitCard = (Card) session.getAttribute("debitCard");
        session.setAttribute("message", null);
        return "Deposit";
    }
    @PostMapping("/deposit")
    public String deposit(@ModelAttribute Transaction transaction, HttpSession session){
        Card debitCard = (Card) session.getAttribute("debitCard");
        if (transactionRepository.checkTransactionLimit(debitCard.getCardNumber(), Constants.TRANSACTION_TYPE_DEPOSIT) > Constants.MAXIMUM_DEPOSIT_WITHDRAW_TIME){
            session.setAttribute("message", "Transaction limit reached, please try again tomorrow");
        } else if (transaction.getAmount() > Constants.MAXIMUM_TRANSACTION_AMOUNT) {
            session.setAttribute("message", "Depositing amount is too large");
        }else {
            Transaction deposit = transactionServices.depositForDebit(debitCard.getCardNumber(), transaction.getAmount());
            session.setAttribute("debitDepositInfo", deposit);
            session.setAttribute("message", "Transaction success");
            session.setAttribute("debitCard", cardServices.findCardById(debitCard.getCardNumber()));
        }
        return "Deposit";
    }

    @GetMapping("/withdraw_form")
    public String withdrawForm(HttpSession session){
        //        Card debitCard = (Card) session.getAttribute("debitCard");
        session.setAttribute("message", null);
        return "Withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@ModelAttribute Transaction transaction, HttpSession session){
        Card debitCard = (Card) session.getAttribute("debitCard");
        if (transactionRepository.checkTransactionLimit(debitCard.getCardNumber(), Constants.TRANSACTION_TYPE_WITHDRAW) > Constants.MAXIMUM_DEPOSIT_WITHDRAW_TIME){
            session.setAttribute("message", "Transaction limit reached, please try again tomorrow");
        } else if (transaction.getAmount() > Constants.MAXIMUM_WITHDRAW_AMOUNT) {
            session.setAttribute("message", "Withdrawing amount is too large");
        }else {
            Transaction deposit = transactionServices.withdraw(debitCard.getCardNumber(), transaction.getAmount());
            session.setAttribute("debitDepositInfo", deposit);
            session.setAttribute("message", "Transaction success");
            session.setAttribute("debitCard", cardServices.findCardById(debitCard.getCardNumber()));
        }
        return "Withdraw";
    }

    @GetMapping("/balance_enquiry")
    public String balanceEnquiry(HttpSession session){
        Card debitCard = (Card) session.getAttribute("debitCard");
        List<Transaction> transactionList = transactionServices.getTransactionRecord(debitCard.getCardNumber());
        session.setAttribute("transactionRecord", transactionList);
        for (Transaction transaction: transactionList){
            System.out.println(transaction.getTransactionId() + " " + transaction.getTransactionType());
        }
        return "BalanceEnquiry";
    }
}
