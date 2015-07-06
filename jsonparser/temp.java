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
        
        String test = "(sentence";
        System.out.println(test.substring(test.lastIndexOf(" ") + 1));


        
        
        String str = "Elegibility: 5309. Randomised: 506 (254 vitamins versus 252 placebo). . Age (years) Overall: 61.4 B-vitamins group: 61.7 (±10.1). Placebo group: 61.1 (± 9.6). . Sex (men): Overall: 61% B-vitamins group: 61%. Placebo group: 61%. . Inclusion criteria: 1. Men and postmenopausal women 40 years old 2. Fasting tHcy 8.5 mol/L 3. No clinical signs/symptoms of cardiovascular disease (CVD). . Exclusion criteria: 1. Fasting triglycerides > 5.64 mmol/L (500 mg/dL). 2. Diabetes mellitus or fasting serum glucose > 6.99 mmol/L (126 mg/dL). 3. Systolic blood pressure ≥ 160 mm Hg and/or diastolic blood pressure ≥ 100 mm Hg. 4. Untreated thyroid disease. 5. Creatinine clearance < 70 mL/min. 6. Life-threatening illness with prognosis 5 years. 7. Five alcoholic drinks daily.";
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
                        Matcher matcher = Pattern.compile("([A-Z].*)").matcher(innerTokens);
                        if(matcher.find()) {
                            keys.add(matcher.group(1));
                        }
                        else{
                            
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
