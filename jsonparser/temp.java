/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author aka324
 */
public class temp {
    public static void main(String args[]){
        
        
        String str = "N = 17, COPD FEV1 pred. mean (SD): 39.0 (15.8) Age mean (SD): 72.2 (5.7) Sex: 100% M INCLUSION CRITERIA: COPD; stable disease EXCLUSION CRITERIA: not explicitly stated as exclusion criteria but as “patients did not have”: history of heart disease; symptoms of active pulmonary infection; involvement in pulmonary rehabilitation programme";
        System.out.println(str);
        String[] tokens = str.split("(?<!\\d)\\.(?!\\d)|(?<=[A-Za-z0-9]\\d)\\.(?=\\s)"); 
        //\s\d\.\s
        //("(?<=\\d)\\.(?=\\s) | (?<!\\d)\\.(?!\\d)
        StringBuilder value = new StringBuilder();
        List<String> keys  = new ArrayList<String>();
        for(String p : tokens){
            if((!p.matches("\\s+")) && (!p.equalsIgnoreCase(""))){
                // Code for extracting keys
//                System.out.println(p);
                if(p.contains(":")){
                    System.out.println(p);
                    String[] tokens1 = p.split(":");
                    for(int i  = 0; i < tokens1.length - 1 ; i++){
                        String innerTokens = tokens1[i];
                        Matcher matcher = Pattern.compile("(\\b[A-Z](?![^(]*\\))(?!.*\\b[A-Z](?![^(]*\\))).*)").matcher(innerTokens);
                        if(matcher.find()) {
                            keys.add(matcher.group(1));
                        }
                        else{
                            keys.add(innerTokens);
                        }
                    }
                    
                }            
//                System.out.println(p);
//                if(!p.matches("\\s\\d\\.\\s"))
//                    System.out.println(p);
//                if(p.contains(":"))
//                    value = new StringBuilder();
////                System.out.println("  ----- \n"+p + "\n  ----- \n");
//                String[] innerTokens = p.split(":");
//                System.out.println(innerTokens[0] + " -> ");
//                for(int i  = 1; i < innerTokens.length; i++)
//                    value.append(innerTokens[i] + ":");
//                String v = value.substring(0, value.length() -1);
//                System.out.println(v);
            }    
        }
        System.out.println(keys.toString());
    }    
}
