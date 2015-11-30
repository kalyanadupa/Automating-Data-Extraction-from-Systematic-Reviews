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
import pdf.KeyPair;

/**
 *
 * @author Abhishek
 */
public class groupKeys {
    public List<Key> gKeys(List<Key> temp) throws FileNotFoundException, IOException{

        List<Key> removeList = new ArrayList<Key>();
        List<KeyPair> kgp = new ArrayList<KeyPair>();
        for(Key k : temp){
            if(k.name.startsWith(" ")){
                k.name = k.name.trim();
                for(Key p : temp){
                    if(k.name.equalsIgnoreCase(p.name) && k.category.contains(p.category) && (k.freq != p.freq)){
                        if(k.freq > p.freq){
                            k.freq = k.freq +p.freq;
                            kgp.add(new KeyPair(k,p));
                            System.out.println("Grouped " + k.name +"("+k.freq+")" +" <- " + p.name+"("+p.freq+")");
                        }
                        if(k.freq < p.freq){
                            p.freq = k.freq +p.freq;
                            kgp.add(new KeyPair(k,p));
                            System.out.println("Grouped " + p.name+"("+p.freq+")" +" <- " + k.name+"("+k.freq+")");
                        }   
                    }
                }
            }
        }

        
        CosineSimilarity cs = new CosineSimilarity();
        for(int i = 0; i < temp.size();i++){
            for(int j = i+1; j < temp.size();j++){
                String x1 = temp.get(i).name.toLowerCase().replaceAll("-", " ");
                String x2 = temp.get(j).name.toLowerCase().replaceAll("-", " ");
                double sim = cs.CosineSimilarity_Score(x1,x2);
                int ed = minDistance(x1,x2);
                if(ed == 1){
                    if ((temp.get(i).freq > temp.get(j).freq) && (temp.get(i).category.contains(temp.get(j).category))) {
                        kgp.add(new KeyPair(temp.get(i),temp.get(j)));
                        System.out.println("Grouped " + temp.get(i).name+"("+temp.get(i).freq+")" +" <- " + temp.get(j).name+"("+temp.get(j).freq+")");
                    } else if ((temp.get(i).freq < temp.get(j).freq) && (temp.get(j).category.contains(temp.get(i).category))) {
                        kgp.add(new KeyPair(temp.get(i),temp.get(j)));
                        System.out.println("Grouped " + temp.get(j).name+"("+temp.get(j).freq+")" +" <- " + temp.get(i).name+"("+temp.get(i).freq+")");
                    }
                }
                else if(sim > 0.7){
                    if((temp.get(i).freq > temp.get(j).freq) && (temp.get(i).category.contains(temp.get(j).category))){
                        kgp.add(new KeyPair(temp.get(i),temp.get(j)));
                        System.out.println("Grouped " + temp.get(i).name+"("+temp.get(i).freq+")" +" <- " + temp.get(j).name+"("+temp.get(j).freq+")");
                    } 
                    else if((temp.get(i).freq < temp.get(j).freq) && (temp.get(j).category.contains(temp.get(i).category))){
                        kgp.add(new KeyPair(temp.get(i),temp.get(j)));
                        System.out.println("Grouped " + temp.get(j).name+"("+temp.get(j).freq+")" +" <- " + temp.get(i).name+"("+temp.get(i).freq+")");
                    }
                }
            }
        }
        

        while(kgp.size() != 0){
            KeyPair kp = kgp.get(0);
            kgp.remove(0);
            List<Key> grp = new ArrayList<Key>();
            List<KeyPair> rmkp = new ArrayList<KeyPair>();
            grp.add(kp.k1);
            grp.add(kp.k2);
            int old = 0;
            while((grp.size() - old) != 0){
                old = grp.size();
                for(KeyPair kpr : kgp){
                    Key xk = find(grp,kpr);
                    if(xk != null){
                        if(!grp.contains(xk))
                            grp.add(xk);
                        rmkp.add(kpr);
                    }
                }
            }
            for(KeyPair rm : rmkp){
                kgp.remove(rm);
            }
            int highFreq = 0;
            int index =0 ;
            Key high = null;
            for(Key nk : grp){
                if(nk.freq >= highFreq)
                    high = nk;
            }
            index = temp.indexOf(high);
//            System.out.println("Index obtained "+ high.name);
            for(Key nk : grp){
                if(!nk.equals(high)){
                    temp.get(index).gKey.add(nk);
                    temp.remove(nk);
//                    System.out.println("Adding "+nk.name +" to "+ high.name );
                }
                System.out.print(nk.name+"("+nk.freq+")\t" );
            }
            System.out.println("");    
        }
        
        return temp;
    }
    
    
    
    public Key find(List<Key> grp, KeyPair kp){
        for(Key kx : grp){
            if(kp.k1.equals(kx))
                return kp.k2;
            else if(kp.k2.equals(kx))
                return kp.k1;
        }
        return null;
    }
    
    
    public int countWords(String s) {

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
            // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
            // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }


    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }
}
