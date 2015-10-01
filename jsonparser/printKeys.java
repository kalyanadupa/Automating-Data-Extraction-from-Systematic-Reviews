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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abhishek
 */
public class printKeys {
    public static void main(String[] argsv) throws FileNotFoundException, IOException {
        List<Key> temp = new ArrayList<Key>();

        BufferedReader br = new BufferedReader(new FileReader(new File("topFreqKeys")));
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] token = line.split("\t");
            Key k = new Key(token[0], Integer.parseInt(token[1]), token[2], countWords(token[0]));
            temp.add(k);
        }
        List<Key> removeList = new ArrayList<Key>();
        for (Key k : temp) {
            if (k.name.startsWith(" ")) {
                k.name = k.name.trim();
                for (Key p : temp) {
                    if (k.name.equalsIgnoreCase(p.name) && k.category.contains(p.category) && (k.freq != p.freq)) {
                        if (k.freq > p.freq) {
                            k.gKey.add(p);
                            k.freq = k.freq + p.freq;
                            removeList.add(p);
                            System.out.println("Grouped " + k.name + "(" + k.freq + ")" + " <- " + p.name + "(" + p.freq + ")");
                        }
                        if (k.freq < p.freq) {
                            p.gKey.add(k);
                            p.freq = k.freq + p.freq;
                            removeList.add(k);
                            System.out.println("Grouped " + p.name + "(" + p.freq + ")" + " <- " + k.name + "(" + k.freq + ")");
                        }
                    }
                }
            }
        }
        for (Key x : removeList) {
            if (x.gKey.isEmpty()) {
                temp.remove(x);
            } else {
                System.out.println("Error" + x.name);
            }
        }

        CosineSimilarity cs = new CosineSimilarity();
        for (int i = 0; i < temp.size(); i++) {
            for (int j = i + 1; j < temp.size(); j++) {
                double sim = cs.CosineSimilarity_Score(temp.get(i).name, temp.get(j).name);
                int ed = minDistance(temp.get(i).name, temp.get(j).name);
                if (ed == 1) {
                    if ((temp.get(i).freq > temp.get(j).freq) && (temp.get(i).category.contains(temp.get(j).category))) {
                        temp.get(i).gKey.add(temp.get(j));
                        System.out.println("Grouped " + temp.get(i).name + "(" + temp.get(i).freq + ")" + " <- " + temp.get(j).name + "(" + temp.get(j).freq + ")");
                    } else if ((temp.get(i).freq < temp.get(j).freq) && (temp.get(j).category.contains(temp.get(i).category))) {
                        temp.get(j).gKey.add(temp.get(i));
                        System.out.println("Grouped " + temp.get(j).name + "(" + temp.get(j).freq + ")" + " <- " + temp.get(i).name + "(" + temp.get(i).freq + ")");
                    }
                } else if (sim > 0.7) {
                    if ((temp.get(i).freq > temp.get(j).freq) && (temp.get(i).category.contains(temp.get(j).category))) {
                        temp.get(i).gKey.add(temp.get(j));
                        System.out.println("Grouped " + temp.get(i).name + "(" + temp.get(i).freq + ")" + " <- " + temp.get(j).name + "(" + temp.get(j).freq + ")");
                    } else if ((temp.get(i).freq < temp.get(j).freq) && (temp.get(j).category.contains(temp.get(i).category))) {
                        temp.get(j).gKey.add(temp.get(i));
                        System.out.println("Grouped " + temp.get(j).name + "(" + temp.get(j).freq + ")" + " <- " + temp.get(i).name + "(" + temp.get(i).freq + ")");
                    }
                }

            }
        }

        for (Key k : temp) {
            if (!k.gKey.isEmpty()) {
                System.out.print(k.name + " <- ");
                for (Key p : k.gKey) {
                    System.out.print(p.name + ", ");
                }
                System.out.println("");
            }

        }
    }

    public static int countWords(String s) {

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

    public static int minDistance(String word1, String word2) {
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
