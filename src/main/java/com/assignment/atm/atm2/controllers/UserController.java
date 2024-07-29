package com.assignment.atm.atm2.controllers;

import com.assignment.atm.atm2.entity.Card;
import com.assignment.atm.atm2.entity.User;
import com.assignment.atm.atm2.service.CardServices;
import com.assignment.atm.atm2.service.Constants;
import com.assignment.atm.atm2.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired private UserServices userServices;

    @GetMapping("/")
    public String loginForm(){
        return "Login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){
        if (userServices.login(user.getUserName(), user.getPassword())){
            redirectAttributes.addFlashAttribute("user_info",user.getUserName());
            return "redirect:/card_list";
        }
        model.addAttribute("message", "Wrong username or password");
        return "Login";
    }
}
