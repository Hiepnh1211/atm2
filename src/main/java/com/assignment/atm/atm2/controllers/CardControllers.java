package com.assignment.atm.atm2.controllers;

import com.assignment.atm.atm2.entity.Card;
import com.assignment.atm.atm2.repository.CardRepository;
import com.assignment.atm.atm2.service.CardServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CardControllers {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardServices cardServices;

    @GetMapping("/products")
    public ModelAndView list() {
        Card card = cardServices.findCardById(3123456789102365L);
        System.out.println(card.getCardStatus());
        List<Card> cardList = cardRepository.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("first.html");
        modelAndView.addObject("list", cardList);
        return modelAndView;
    }

}
