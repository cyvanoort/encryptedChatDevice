/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onetimepad;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author cyvan
 * a class to read the key for decryption.
 */
public class keyReader {
    public List<Integer> Read(String path) throws FileNotFoundException, IOException{
        List<Integer> key = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String everything = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }
        String[] sp = everything.split("\\[");
        String[] keys = sp[1].split("\\]");
        
        
        List<String> stringkeys = Arrays.asList(keys[0].split(", "));
        for(String s : stringkeys) key.add(Integer.valueOf(s));
        return key;
    }
}
