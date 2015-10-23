/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static jsonparser.printDistinctKeys.findKeys;
import static jsonparser.printDistinctKeys.temp;
import static jsonparser.printKeyPair.countWords;

/**
 *
 * @author Abhishek Date: 10/12/2015
 * Abhishek, one more thing. Is it possible to put the
 * frequencies for each of the "automatic" elements as well?
 */
public class printKeyFreq {
    public static List<Key> temp = new ArrayList<Key>();

    public static void main(String[] argsv) throws FileNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new FileReader(new File("fullSheet")));
        String line = "";
        while ((line = br.readLine()) != null) {
            if(line != ""){
                
                String[] token = line.split("\t");
                Key k;
                if(token.length == 3)
                    k = new Key(token[0], token[1]);
                else{
                    k = new Key(token[0], token[1], token[5]);
                }    
                temp.add(k);
            }
        }

        br = new BufferedReader(new FileReader(new File("topFreqKeys")));
        line = "";
        while ((line = br.readLine()) != null) {
            String[] token = line.split("\t");
            if (!findKeys(token[0], Integer.parseInt(token[1]), token[2])) {
//                System.out.println(token[0]+" ERR");
            }
            
        }
        for (Key p : temp) {
            //if(p.category.equalsIgnoreCase("Participants")){
                if(p.groupedKeys != "")
                    System.out.println(p.name + "\t" + p.freq+"\t"+p.category + "\tAutomatic"+"\t"+"\t\t"+p.groupedKeys);
                else
                    System.out.println(p.name + "\t" + p.freq+"\t"+p.category + "\tAutomatic"+"\t"+"\t"+"\t");
            //}
        }
    }
    public static boolean findKeys(String keyName, int f, String cat) {
        keyName = keyName.trim();
        for (Key k : temp) {
            if (k.name.equalsIgnoreCase(keyName) && (k.category.equalsIgnoreCase(cat))) {
                k.freq = f;
                return true;
            } 
        }
        return false;
    }
}
