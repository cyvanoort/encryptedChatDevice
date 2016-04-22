/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onetimepad;

import java.util.List;

/**
 *
 * @author cyvan
 * 
 * custom class to deal with ceasar ciphers of characters.
 */
public class caesarCipher {    
    /**
     * 
     * @param str the string of text you want to encrypt
     * @param keys the keys used to decrypt
     * @return a string containing the encrypted message.
     * 
     * a custom method to deal with encrypting strings.
     */
    String encrypt(String str, List<Integer> keys){
    String encrypted="";
    for(int i=0;i<str.length();i++)
    {
        //stores ascii value of character in the string at index 'i'
        int c=str.charAt(i);
        //encryption logic for uppercase letters
        if(Character.isUpperCase(c))
        {
            c=c+(keys.get(i)%26);
            //if c value exceeds the ascii value of 'Z' reduce it by subtracting 26(no.of alphabets) to keep in boundaries of ascii values of 'A' and 'Z'
            if(c>'Z')
                c=c-26;
        }
        //encryption logic for lowercase letters
        else if(Character.isLowerCase(c))
        {
            c=c+(keys.get(i)%26);
            //if c value exceeds the ascii value of 'z' reduce it by subtracting 26(no.of alphabets) to keep in boundaries of ascii values of 'a' and 'z'
            if(c>'z')
                c=c-26;
        }
        //concatinate the encrypted characters/strings
        encrypted=encrypted+(char) c;
    }
    return encrypted;
}

    /**
     * 
     * @param str the string you want to decrypt
     * @param keys the keys used to decrypt
     * @return the decrypted string.
     * 
     * a custom method to deal with decryption using the caesar cypher.
     */
String decrypt(String str, List<Integer> keys){
    String decrypted="";
    for(int i=0;i<str.length();i++)
    {
        //stores ascii value of character in the string at index 'i'
        int c=str.charAt(i);
        //decryption logic for uppercase letters
        if(Character.isUpperCase(c))
        {
            c=c-(keys.get(i)%26);
            //if c value deceed the ascii value of 'A' increase it by adding 26(no.of alphabets) to keep in boundaries of ascii values of 'A' and 'Z'
            if(c<'A')
                c=c+26;
        }
        //decryption logic for uppercase letters
        else if(Character.isLowerCase(c))
        {
            c=c-(keys.get(i)%26);
            //if c value deceed the ascii value of 'A' increase it by adding 26(no.of alphabets) to keep in boundaries of ascii values of 'A' and 'Z'
            if(c<'a')
                c=c+26;
        }
        //concatinate the decrypted characters/strings
        decrypted=decrypted+(char) c;
    }
    return decrypted;
} 
}
