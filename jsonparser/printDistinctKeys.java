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
    public static List<Key> temp = new ArrayList<Key>();
    public static void main(String[] argsv) throws FileNotFoundException, IOException{

        BufferedReader br = new BufferedReader(new FileReader(new File("grouped Keys.txt")));
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] token = line.split("#");
            //System.out.println(token[0].substring(token[0].lastIndexOf('(')+1,token[0].lastIndexOf(')')));
            Key k = new Key(token[0].substring(0,token[0].lastIndexOf('(')),Integer.parseInt(token[0].substring(token[0].lastIndexOf('(')+1,token[0].lastIndexOf(')'))));
            for(int i = 1; i < token.length;i++){
                Key p = new Key(token[i].substring(0,token[i].lastIndexOf('(')),Integer.parseInt(token[i].substring(token[i].lastIndexOf('(')+1,token[i].lastIndexOf(')'))));
                k.gKey.add(p);
            }   
            temp.add(k);
        }
        
        br = new BufferedReader(new FileReader(new File("topFreqKeys")));
        line = "";
        while ((line = br.readLine()) != null) {
            String[] token = line.split("\t");
            if(!findKeys(token[0], Integer.parseInt(token[1]), token[2])){
                Key k = new Key(token[0], Integer.parseInt(token[1]), token[2], countWords(token[0]));
                temp.add(k);
            }
        }
        for(Key p : temp){
            System.out.println(p.name + "\t"+ p.category);
        }
    }
    
    public static boolean findKeys(String keyName,int f,String cat){
        keyName = keyName.trim();
        for(Key k : temp){
            if(k.name.equalsIgnoreCase(keyName) && (k.freq == f)){
                k.category = cat;
                return true;
            }
                
            else{
                for(Key x : k.gKey){
                    if(x.name.equalsIgnoreCase(keyName)&&(x.freq == f)){
                        x.category = cat;
                        return true;
                    } 
                }
            }
        }
        return false;
    }
    
    
}
