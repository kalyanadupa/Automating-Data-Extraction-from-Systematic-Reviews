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
public class studies {
    String method;
    String interventions;
    String notes;
    String outcomes;
    String participants;  
    String label;
    
    public studies(){
        this.method = "";
        this.interventions = "";
        this.notes = "";
        this.outcomes = "";
        this.participants = "";
        this.label = "";
        
    }
    
    public void print(){
        System.out.println("Methods : " + this.method);
        System.out.println("Interventions : " + this.interventions);
        System.out.println("Notes : " + this.notes);
        System.out.println("Outcomes : " + this.outcomes);
        System.out.println("Participants : " + this.participants);        
        System.out.println("Label : " + this.label);        
    }
    
    public void parseParticipants(){
        String str = this.participants;
        String[] tokens = str.split("(?<!\\d)\\.(?!\\d)|(?<=[A-Za-z0-9]\\d)\\.(?=\\s)"); 
        List<String> keys = new ArrayList<String>();
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
            }    
        }
        System.out.println(keys.toString());
    }
    
}
