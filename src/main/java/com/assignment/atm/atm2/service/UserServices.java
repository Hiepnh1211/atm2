package com.assignment.atm.atm2.service;

import com.assignment.atm.atm2.entity.User;
import com.assignment.atm.atm2.repository.CardRepository;
import com.assignment.atm.atm2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private Utilities utilities;
    Random random = new Random();
    public User getUserByName(String name){
        return userRepository.findById(name).orElse(null);
    }
    public void updateBalance(String id, double amount, String type){
        User user = userRepository.findById(id).orElse(null);
        if (user != null){
            if (type.equals(Constants.FUNCTION_DEPOSIT) || type.equals(Constants.FUNCTION_RECEIVE)){
                user.setBalance(user.getBalance() + amount);
                userRepository.save(user);
            }else if (type.equals(Constants.FUNCTION_WITHDRAW) || type.equals(Constants.FUNCTION_SEND)){
                user.setBalance(user.getBalance() - amount);
                userRepository.save(user);
            }
        }
    }

    public User getUserByCardNumber(long id){
        return userRepository.findById(cardRepository.getUserByCardNumber(id)).orElse(null);
    }
    public String userNameGenerator(String name) {
        int num = userRepository.nameCount(name);
        StringBuilder userName = new StringBuilder();
        for (String part: name.split(" ")){
            userName.append(part);
        }
        userName.append(num+1);
        return userName.toString();
    }
    public User createUser(String name, String contactNumber, String gender, String address){
        String username = userNameGenerator(name);
        int password = random.nextInt(10000);
        userRepository.userCreation(userNameGenerator(name), String.valueOf(random.nextInt(10000)), name, contactNumber, gender, address);
        return new User(username, String.valueOf(password), name, contactNumber, gender, address, Constants.INITIAL_BALANCE, Constants.USER_ROLE);
    }

    public boolean login(String username, String password){
        return utilities.idGenerator(username, password, null).equals(userRepository.login(username));
    }
}
