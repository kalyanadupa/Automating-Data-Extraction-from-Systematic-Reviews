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
        
        String test = "Chinese 100 patients (50 in shengmai group, M/F 30/20, mean age 58 years, duration of heart failure 4.8 years, heart function class 2/3/4";
        System.out.println(test.substring(test.lastIndexOf(",") + 1));


        
        String str = "N: 283 randomised patients (folic acid: 140 versus standard care: 143) . Sex (% men): folic acid: 69% versus standard care: 70%. . Age (mean): folic acid: 59 years versus standard care: 59 . Homocysteine levels at baseline: not reported . Inclusion criteria (one of the following): 1. Myocardial infarction. 2. Total cholesterol value at admission or within 24 hours after onset of symptoms: > 6.5 μmol/L (251 mg/dL), 3. Elevation of CK-MB at least 2 times upper the limit of normal function, 4. Markedly increased chest pain lasting more than 30 minutes or classical ECG changes . Exclusion criteria: 1. Age under 18 years, 2. Use of lipid-lowering agents within the previous 3 months, 3. High triglyceride levels > 4.5 μmol/L, 4. Known familial dyslipidaemia, 5. Low vitamin B12 levels, 6. Hyperhomocysteinemia (total plasma homocysteine > 18 μmol/L) or a known disturbed methionine loading test (total plasma homocysteine > 47 μmol/L), 7. Severe renal failure (serum creatinine > 180 μmol/L), 8. Hepatic disease, 9. Severe heart failure (New York Heart Association class IV), 10. Scheduled percutaneous coronary intervention (PCI) or coronary artery bypass graft (CABG) operation.";
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
                Pattern regex = Pattern.compile(
                        ":         # Match a colon\n"
                        + "(?!       # only if it's not followed by...\n"
                        + " [^(]*    #   any number of characters except opening parens\n"
                        + " \\)      #   followed by a closing parens\n"
                        + ")         # End of lookahead",
                        Pattern.COMMENTS);
                Matcher colon = regex.matcher(p);
                if(colon.find()){
                    String[] tokens1 = p.split(":(?![^(]*\\))");
                    for(int i  = 0; i < tokens1.length - 1 ; i++){
                        String innerTokens = tokens1[i];
                        innerTokens = innerTokens.substring(innerTokens.lastIndexOf(",") + 1);
                        System.out.println(innerTokens);
                        Matcher matcher;
                        if(innerTokens.contains("CRITERIA"))
                            matcher = Pattern.compile("((\\w+\\s*){1,2})$").matcher(innerTokens);
                        else
                            matcher = Pattern.compile("(\\b[A-Z](?![^(]*\\))(?!.*\\b[A-Z](?![^(]*\\))).*)").matcher(innerTokens);
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
