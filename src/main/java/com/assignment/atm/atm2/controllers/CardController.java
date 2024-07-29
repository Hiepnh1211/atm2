package com.assignment.atm.atm2.controllers;

import com.assignment.atm.atm2.entity.Card;
import com.assignment.atm.atm2.entity.User;
import com.assignment.atm.atm2.repository.CardRepository;
import com.assignment.atm.atm2.service.CardServices;
import com.assignment.atm.atm2.service.Constants;
import com.assignment.atm.atm2.service.UserServices;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CardController {
    @Autowired private UserServices userServices;
    @Autowired private CardServices cardServices;
    @GetMapping("/card_list")
    public String cardList(Model model){
        String username = (String) model.getAttribute("user_info");
        User user = userServices.getUserByName(username);
        List<Card> cardList = user.getCardList();
        if (cardList != null){
            model.addAttribute("cardList", cardList);
        }
        model.addAttribute("user", user);
        return "CardList";
    }
    @PostMapping("/user_menu")
    public String userMenu(@ModelAttribute Card card, HttpSession session){
        card = cardServices.findCardById(card.getCardNumber());
        String cardNumber = String.valueOf(card.getCardNumber());
        String status = card.getCardStatus();
        if (cardNumber.startsWith(Constants.DEBIT_CARD_STARTER) && status.equals(Constants.CARD_STATUS_ACTIVATED)){
            session.setAttribute("debitCard", card);
            return "DebitUserMenu";
        } else if (cardNumber.startsWith(Constants.CREDIT_CARD_STARTER) && status.equals(Constants.CARD_STATUS_ACTIVATED)) {
            session.setAttribute("creditCard", card);
            return "CreditUserMenu";
        }
        session.setAttribute("cardError", "Card Invalid");
        return "CardList";
    }
}
