/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onetimepad;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cyvan
 * this class is created to deal with random numbers.
 */
public class randomGenerator {
    /**
     * creates a serie of keys depending on the amount specified.
     * @param amount
     * @return List of keys with the amount specified.
     */
    List<Integer> randomNumber(int amount) {
        List<Integer> key = new ArrayList<>();
        SecureRandom rand = new SecureRandom();
        for(int i=0;i<=amount;i++){
            key.add(rand.nextInt(26) + 1);
        }
        return key;
    }
}
