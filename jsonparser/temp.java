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
//        String pou = " yeh isu";
//        Pattern pz = Pattern.compile("\\s\\d\\.\\s");
//        if(matchesPattern(pz, pou) != null)
//            System.out.println(matchesPattern(pz, pou));
        
        
        
        String str = "Clinical condition: survivors of myocardial infarction in secondary care hospitals 1. Potential participants invited by mail: 83,237 2. Attended screening visit: 34,780 3. Entered pre-randomisation run-in-phase: 19,190. Quote: ”Run-in treatment involved placebo vitamin tablets (and 20 mg simvastatin daily, which allowed baseline lipid levels to be assessed after all participants had received the same statin therapy) (Continued) (page 2487) 4. Randomised: 12,064 (folic acid and B12: 6033 versus placebo: 6031) . Gender (% men) Men: 10,012 Women: 2052 1. Folic acid and B12: 83% 2. Placebo: 83% . Age (at randomisation) Mean (SD) age of 64.2 (8.9) years Folic acid and vitamin B12: 1. < 60 years: 31% 2. ≥ 60 years to < 70 years: 40% 3. ≥ 70 years: 29% Placebo: 1. < 60 years: 31% 2. ≥ 60 years to < 70 years: 40% 3. ≥ 70 years: 29% . Inclusion criteria: 1. Men and women 2. Aged 18 to 80 years 3. History of myocardial infarction 4. Had no clear indication for folic acid 5. Blood cholesterol levels of at least 135 mg/dL if already taking a statin medication or 174 mg/dL if not (to convert cholesterol to mmol/L, multiply by 0.0259) . Exclusion criteria: 1. Chronic liver, renal or muscle disease 2. History of any cancer (except non-melanoma skin cancer) 3. Use of potentially interacting medications";
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
                Pattern pz = Pattern.compile("\\s\\d\\.\\s");
                if(colon.find()){
                    String[] tokens1 = p.split(":(?![^(]*\\))");
                    for(int i  = 0; i < tokens1.length - 1 ; i++){
                        String innerTokens = tokens1[i];
                        innerTokens = innerTokens.substring(innerTokens.lastIndexOf(",") + 1);
                        if(innerTokens.contains(";"))
                            innerTokens = innerTokens.substring(innerTokens.lastIndexOf(";") + 1);
                        System.out.println(innerTokens);
                        Matcher matcher;
                        if(innerTokens.contains("CRITERIA"))
                            matcher = Pattern.compile("((\\w+\\s*){1,2})$").matcher(innerTokens);
                        else if(innerTokens.contains("versus")){
                            matcher = Pattern.compile("(?<=versus )(.*)$").matcher(innerTokens);
                        }
                        else if(matchesPattern(pz, innerTokens) != null)
                            matcher = Pattern.compile("(?<=\\s\\d\\.\\s)(.*)$").matcher(innerTokens);
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
            }    
        }
        System.out.println(keys.toString());
    }
    
    
    private static String matchesPattern(Pattern p, String sentence) {
        Matcher m = p.matcher(sentence);

        if (m.find()) {
            return m.group();
        }

        return null;
    }
}
