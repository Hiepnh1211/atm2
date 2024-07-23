package com.assignment.atm.atm2.service;

import com.assignment.atm.atm2.entity.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
@Service
public class Utilities {
    public boolean checkName(String name) {
        return name.length() >= Constants.MINIMUM_NAME_LENGTH && name.length() <= Constants.MAXIMUM_NAME_LENGTH;
    }
    public boolean checkContactNumber(String contactNumber) {
        if(contactNumber.length() < Constants.MAXIMUM_CONTACT_NUMBER_LENGTH) {
            return false;
        }
        char[] number  = contactNumber.toCharArray();
        for (char c : number) {
            if (Character.isAlphabetic(c)) {
                return false;
            }
        }
        return true;
    }
    public boolean checkBalance(User user, double amount) {
        return user.getBalance() > amount;
    }
    public String idGenerator(String var1, String var2, String var3) {
        String base = var1 + var2 + var3;
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    public String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}
