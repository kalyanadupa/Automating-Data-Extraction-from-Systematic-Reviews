/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author aka324
 */
public class studies {
    public String method;
    public String interventions;
    public String notes;
    public String outcomes;
    public String participants;  
    public String label;
    
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
                        else if(innerTokens.contains("versus"))
                            matcher = Pattern.compile("(?<=versus )(.*)$").matcher(innerTokens);
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
    
    
    public void parseCriteria(List<String> incl, List <String> excl){
        String str = this.participants;
        if ((str.toLowerCase().contains("inclusion criteria")) && (str.toLowerCase().contains("exclusion criteria"))) {
            Matcher matcher;
            matcher = Pattern.compile("(Inclusion criteria|INCLUSION CRITERIA|inclusion criteria)\\s*(:|\\.)(.*)(Exclusion criteria|EXCLUSION CRITERIA|exclusion criteria)\\s*(:|\\.)(.*)").matcher(str);
            if (matcher.find()) {
                incl.add(matcher.group(3));
                excl.add(matcher.group(6));
            }
        } else if (str.toLowerCase().contains("inclusion criteria")) {
            Matcher matcher1;
            matcher1 = Pattern.compile("(Inclusion criteria|INCLUSION CRITERIA|inclusion criteria)\\s*(:|\\.)(.*)").matcher(str);
            if (matcher1.find()) {
                incl.add(matcher1.group(3));
            }
        }
        else if (str.toLowerCase().contains("exclusion criteria")) {
            Matcher matcher1;
            matcher1 = Pattern.compile("(Exclusion criteria|EXCLUSION CRITERIA|exclusion criteria)\\s*(:|\\.)(.*)").matcher(str);
            if (matcher1.find()) {
                excl.add(matcher1.group(3));
            }
        }
    }
    
    
    public void getKeys(HashMap<String, Integer> pKList,HashMap<String, Integer> iKList,HashMap<String, Integer> mKList,HashMap<String, Integer> oKList){
        parseKeys(this.participants,pKList);
        parseKeys(this.interventions,iKList);
        parseKeys(this.method,mKList);
        parseKeys(this.outcomes,oKList);
    }
    
    public void parseKeys(String parseString,HashMap<String, Integer> keys ){
        String x;
        String str = parseString;
        String[] tokens = str.split("(?<!\\d)\\.(?!\\d)|(?<=[A-Za-z0-9]\\d)\\.(?=\\s)"); 
        Pattern pz = Pattern.compile("\\s\\d\\.\\s");
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
                        if (innerTokens.contains(";")) {
                            innerTokens = innerTokens.substring(innerTokens.lastIndexOf(";") + 1);
                        }
                        Matcher matcher;
                        if(innerTokens.contains("CRITERIA"))
                            matcher = Pattern.compile("((\\w+\\s*){1,2})$").matcher(innerTokens);
                        else if(innerTokens.contains("versus"))
                            matcher = Pattern.compile("(?<=versus )(.*)$").matcher(innerTokens);
                        else
                            matcher = Pattern.compile("(\\b[A-Z](?![^(]*\\))(?!.*\\b[A-Z](?![^(]*\\))).*)").matcher(innerTokens);
                        if(matcher.find()) {
                            x = matcher.group(1);
                            if(matchesPattern(pz, x) != null){
                                Matcher matcherNumbering = Pattern.compile("(?<=\\s\\d\\.\\s)(.*)$").matcher(x);
                                if(matcherNumbering.find())
                                    x = matcherNumbering.group(1);
                            }
                            if (keys.get(x) == null) {
                                keys.put(x, 1);
                            } else {
                                keys.put(x, keys.get(x) + 1);
                            }
                        }
                        else{
                            if (keys.get(innerTokens) == null) {
                                keys.put(innerTokens, 1);
                            } else {
                                keys.put(innerTokens, keys.get(innerTokens) + 1);
                            }
                        }
                    }
                    
                }  
            }    
        }

    }
    
    
    
    //Misc Methods
    boolean listContains(List<String> myList,String search){
        search = search.toLowerCase();
        for (String str : myList) {
            if (str.trim().toLowerCase().contains(search)) {
                return true;
            }
        }
        return false;
        
    }
    
    private static String matchesPattern(Pattern p, String sentence) {
        Matcher m = p.matcher(sentence);

        if (m.find()) {
            return m.group();
        }

        return null;
    }
}
