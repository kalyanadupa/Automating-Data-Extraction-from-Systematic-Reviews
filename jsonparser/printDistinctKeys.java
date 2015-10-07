package jsonparser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jsonparser.Key;
import static jsonparser.printKeyPair.countWords;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abhishek
 * This method is for the meeting on 10/07/2015 for printing all the keys with freq > 10 
 * it also checks for grouped keys and prints them 
 */
public class printDistinctKeys {
    public static void main(String[] argsv) throws FileNotFoundException, IOException{
        List<Key> temp = new ArrayList<Key>();
        BufferedReader br = new BufferedReader(new FileReader(new File("grouped Keys.txt")));
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] token = line.split("#");
            Key k = new Key(token[0].substring(0,token[0].indexOf('(')));
            System.out.println(token[0].substring(0,token[0].indexOf('(')));
            for(int i = 1; i < token.length;i++){
                Key p = new Key(token[i].substring(0,token[i].indexOf('(')));
                System.out.println(token[i].substring(0,token[i].indexOf('(')));
                k.gKey.add(p);
            }   
            temp.add(k);
        }
    }
    
    
}
