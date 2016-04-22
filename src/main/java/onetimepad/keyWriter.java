/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onetimepad;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cyvan
 * 
 * a class used for writing the key to a file for sharing.
 */
public class keyWriter {
    public boolean Write(String key, String path){
        try {
            try (PrintWriter pw = new PrintWriter(path, "UTF-8")) {
                pw.println(key);
            }
            return true;
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(keyWriter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
}
