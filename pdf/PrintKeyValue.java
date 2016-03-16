/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import jsonparser.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author aka324
 */
public class PrintKeyValue {
    static List<String> set= new ArrayList<String>();
    static HashMap<String, Key> kMap = new HashMap<String, Key>();
    public static void main(String argsv[]) throws FileNotFoundException, IOException, ParseException{
        HashMap<String, Integer> pKey = new HashMap<String, Integer>();
        HashMap<String, Integer> mKey = new HashMap<String, Integer>();
        HashMap<String, Integer> oKey = new HashMap<String, Integer>();
        HashMap<String, Integer> iKey = new HashMap<String, Integer>();
        //results_cochrane_HF.json
        System.out.println("reading");
        String fileName = "firstFile.json";
//        String fileName = "results_cochrane_HF.json";
        FileReader fileReader = new FileReader(fileName);
        List<String> orderL = new ArrayList<String>();
        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        
        StringBuilder str = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            str = str.append(line + " ");
            if(line.contains("\"Methods\":"))
                orderL.add("Methods");
            else if(line.contains("\"Interventions\":"))
                orderL.add("Interventions");
            else if(line.contains("\"Notes\":"))
                orderL.add("Notes");
            else if(line.contains("\"Outcomes\":"))
                orderL.add("Outcomes");
            else if(line.contains("\"Participants\":"))
                orderL.add("Participants");
            else if(line.contains("\"Label\":"))
                orderL.add("Label");
            else if(line.contains("\"Filename\":"))
                orderL.add("Filename");
            
        }
        String jsonText = str.toString();
        System.out.println("Done");
        
        
        JSONParser parser = new JSONParser();
        KeyFinder finder = new KeyFinder();
        
        //List<String> getValues(String lookFor,String jsonText)
        
        List<String> MethodsL = getValues("Methods",jsonText);
        List<String> InterventionsL = getValues("Interventions",jsonText);
        List<String> NotesL = getValues("Notes",jsonText);
        List<String> OutcomesL = getValues("Outcomes",jsonText);
        List<String> ParticipantsL = getValues("Participants",jsonText);
        List<String> LabelL = getValues("Label",jsonText);
        List<String> FilenameL = getValues("Filename",jsonText);
        
        
        
        
        List<fName> fnL = new ArrayList<fName>();
        List<studies> stuL = new ArrayList<studies>();
        studies currSt = new studies();
        fName currFN = new fName();
        for(String ptr : orderL){
            if(ptr.equalsIgnoreCase("Methods")){
                currSt.method = MethodsL.get(0);
                MethodsL.remove(0);
            }
            else if (ptr.equalsIgnoreCase("Interventions")) {
                currSt.interventions = InterventionsL.get(0);
                InterventionsL.remove(0);
            }
            else if (ptr.equalsIgnoreCase("Notes")) {
                currSt.notes = NotesL.get(0);
                NotesL.remove(0);
            }
            else if (ptr.equalsIgnoreCase("Outcomes")) {
                currSt.outcomes = OutcomesL.get(0);
                OutcomesL.remove(0);
            }
            else if (ptr.equalsIgnoreCase("Participants")) {
                currSt.participants = ParticipantsL.get(0);
                ParticipantsL.remove(0);
            }
            else if (ptr.equalsIgnoreCase("Label")) {
                currSt.label = LabelL.get(0);
                LabelL.remove(0);
                stuL.add(currSt);
                currSt = new studies();
            }
            else if (ptr.equalsIgnoreCase("Filename")) {
                currFN.fileName = FilenameL.get(0);
                FilenameL.remove(0);
                currFN.stuL = stuL;
                fnL.add(currFN);
                stuL = new ArrayList<studies>();
                currFN = new fName();
            }
        }

        
        for(fName tempFN : fnL){
            //tempFN.printFName();
            // Write the PDF file text to a text file
            PDDocument pd;
            BufferedWriter wr;
            try {
                File input = new File(tempFN.fileName);
                File output = new File("SampleText.txt");
                pd = PDDocument.load(input);
                PDFTextStripper stripper = new PDFTextStripper();
                wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
                stripper.writeText(pd, wr);
                if (pd != null) {
                    pd.close();
                }
                wr.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Read the text file and start processing
            boolean meth = false;
            boolean part = false;
            boolean inter = false;
            boolean out = false;
            boolean not = false;
            boolean lab = false;
            
            for(int i = 0; i < tempFN.stuL.size(); i++){
                BufferedReader br = new BufferedReader(new FileReader(new File("SampleText.txt")));
                line = "";
                while ((line = br.readLine()) != null) {
                    if (line.equalsIgnoreCase(tempFN.stuL.get(i).label)) {
                        lab = true;
                    }
                    if (i + 1 < tempFN.stuL.size()) {
                        if (line.equalsIgnoreCase(tempFN.stuL.get(i + 1).label)) {
                            lab = false;
                            break;
                        }
                    }
                    if(lab){
                        if (line.startsWith("Methods ")) {
                            meth = true;
                            part = false;
                            inter = false;
                            out = false;
                            not = false;
                            String find = line.substring(line.indexOf(" ") + 1, line.length());
                            String nL = tempFN.stuL.get(i).method;
                            try{
//                                System.out.println("oldM " + nL);
//                                System.out.println("find " + find);
                                if(nL.contains(find))
                                    tempFN.stuL.get(i).method = new StringBuffer(nL).insert(nL.indexOf(find)+find.length(), "<>").toString();
//                                System.out.println("newL "+ tempFN.stuL.get(i).method);
                            }
                            catch(Exception ex){
                                System.out.println("** Error **");
                                System.out.println("Find - " + find);
                                System.out.println("nL - " + nL);
                            }
                            
                        } else if (line.startsWith("Participants ")) {
                            part = true;
                            meth = false;
                            inter = false;
                            out = false;
                            not = false;
                            String find = line.substring(line.indexOf(" ") + 1, line.length());
                            String nL = tempFN.stuL.get(i).participants;
                            try {
                                if(nL.contains(find))
                                    tempFN.stuL.get(i).participants = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "<>").toString();
                                
                            } catch (Exception ex) {
                                System.out.println("** Error **");
                                System.out.println("Find - " + find);
                                System.out.println("nL - " + nL);
                            }
                        } else if (line.startsWith("Interventions ")) {
                            meth = false;
                            part = false;
                            inter = true;
                            out = false;
                            not = false;
                            String find = line.substring(line.indexOf(" ") + 1, line.length());
                            String nL = tempFN.stuL.get(i).interventions;
                            try {
                                if(nL.contains(find))
                                    tempFN.stuL.get(i).interventions = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "<>").toString();
                            } catch (Exception ex) {
                                System.out.println("** Error **");
                                System.out.println("Find - " + find);
                                System.out.println("nL - " + nL);
                            }
                            
                        } else if (line.startsWith("Outcomes ")) {
                            meth = false;
                            part = false;
                            inter = false;
                            out = true;
                            not = false;
                            String find = line.substring(line.indexOf(" ") + 1, line.length());
                            String nL = tempFN.stuL.get(i).outcomes;
                            try{
                                if(nL.contains(find))
                                    tempFN.stuL.get(i).outcomes = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "<>").toString();
                            }
                            catch(Exception ex){
                                System.out.println("** Error **");
                                System.out.println("Find - " +find);                                
                                System.out.println("nL - " +nL);                                
                            }
                            
                        } else if (line.startsWith("Notes ")) {
                            meth = false;
                            part = false;
                            inter = false;
                            out = false;
                            not = true;
                        }
                        if(meth){
                            
                            String find = line;
                            String nL = tempFN.stuL.get(i).method;
                            if(nL.contains(find)){
//                                System.out.println("oldM "+ nL);
//                                System.out.println("find "+ find);
                                tempFN.stuL.get(i).method = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "<>").toString();
//                                System.out.println("newM "+ tempFN.stuL.get(i).method);
                            }
                        } else if(part){
                            String find = line;
                            String nL = tempFN.stuL.get(i).participants;
                            if (nL.contains(find)) {
                                tempFN.stuL.get(i).participants = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "<>").toString();
                            }
                        } else if(out){
                            String find = line;
                            String nL = tempFN.stuL.get(i).outcomes;
                            if (nL.contains(find)) {
                                tempFN.stuL.get(i).outcomes = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "<>").toString();
                            }
                        } else if (inter) {
                            String find = line;
                            String nL = tempFN.stuL.get(i).interventions;
                            if (nL.contains(find)) {
                                tempFN.stuL.get(i).interventions = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "<>").toString();
                            }
                        } 
                    }
                }
                br.close();
            }
            
            //tempFN.printFName();
        }
        
        
        //Printing the inner Keys and Values
        System.out.println("\n\n%$ Par %$\n\n");
        for (fName tempFN : fnL) {
            for (int i = 0; i < tempFN.stuL.size(); i++) {
                iKeyValues(tempFN.stuL.get(i).participants.replaceAll(";", "<>"));
                printIK();
                set = new ArrayList<String>();
            }
            
        }
        System.out.println("\n\n%$ out %$\n\n");        
        for (fName tempFN : fnL) {
            for (int i = 0; i < tempFN.stuL.size(); i++) {
                iKeyValues(tempFN.stuL.get(i).outcomes);
                printIK();
                set = new ArrayList<String>();
            }
        }
        System.out.println("\n\n%$ met %$\n\n");        
        for (fName tempFN : fnL) {
            for (int i = 0; i < tempFN.stuL.size(); i++) {
                iKeyValues(tempFN.stuL.get(i).method);
                printIK();
                set = new ArrayList<String>();
            }
        }
        System.out.println("\n\n%$ int %$\n\n");        
        for (fName tempFN : fnL) {
            for (int i = 0; i < tempFN.stuL.size(); i++) {
                iKeyValues(tempFN.stuL.get(i).interventions);
                printIK();
                set = new ArrayList<String>();
            }
        }
        //Remove this if you don't want Inner Keys
        //Removed start of comment delimeter here
        
        // Printing in console
        System.out.println("First File. json Starts here ");
                
        for (fName tempFN : fnL) {
            //tempFN.printFName();
            System.out.println("\n=====\n");
            for(int i = 0; i < tempFN.stuL.size(); i++){
                System.out.println("participants");
                System.out.println(tempFN.stuL.get(i).participants);
                printKeyValues(tempFN.stuL.get(i).participants, tempFN.stuL.get(i).pKeyV);
                System.out.println("\n++++++\n");
                System.out.println("outcomes");
                System.out.println(tempFN.stuL.get(i).outcomes);
                printKeyValues(tempFN.stuL.get(i).outcomes, tempFN.stuL.get(i).oKeyV);
                System.out.println("\n++++++\n");
                System.out.println("methods");
                System.out.println(tempFN.stuL.get(i).method);
                printKeyValues(tempFN.stuL.get(i).method, tempFN.stuL.get(i).mKeyV);
                System.out.println("\n++++++\n");
                System.out.println("interventions");
                System.out.println(tempFN.stuL.get(i).interventions);
                printKeyValues(tempFN.stuL.get(i).interventions, tempFN.stuL.get(i).iKeyV);
                System.out.println("\n++++++\n");
            }
            
//            System.out.println("+++");
            //fillValues(tempFN.stuL.get(0).participants,tempFN.stuL.get(0).pKeyV);
        }
        
        

        //Printing in Data Structure
        System.out.println("031516 SKV");
               
        for (fName tempFN : fnL) {
            //tempFN.printFName();
            for (int i = 0; i < tempFN.stuL.size(); i++) {
                fillValues(tempFN.stuL.get(i).participants, tempFN.stuL.get(i).pKeyV,"Participants",pKey);
                fillValues(tempFN.stuL.get(i).outcomes, tempFN.stuL.get(i).oKeyV,"Outcomes",oKey);
                fillValues(tempFN.stuL.get(i).method, tempFN.stuL.get(i).mKeyV,"Methods",mKey);
                fillValues(tempFN.stuL.get(i).interventions, tempFN.stuL.get(i).iKeyV,"Intervention",iKey);
            }
        }
        System.out.println("031516 EKV "+kMap.size());
        
        
        //Calculating keys and printing frequency
        List<Key> temp = new ArrayList<Key>();
        printFreqMap("Participants",pKey,temp);
        printFreqMap("Outcomes",oKey,temp);
        printFreqMap("Methods",mKey,temp);
        printFreqMap("Intervention",iKey,temp);
        
        
        //Grouping Keys
        System.out.println("\n*** Grouping ***\n");

                
        groupKeys gk = new groupKeys();
        temp = gk.gKeys(temp);
        
        System.out.println("\n\n*&^ Printing &$*\n\n");
        System.out.println("Key\tFrequency\tCategory");
        for(Key kk : temp){
            StringBuilder sb = new StringBuilder(kk.name+"\t"+kk.freq+"\t"+kk.category+"\t");            
            if(!kk.gKey.isEmpty()){
                for(Key kx : kk.gKey)
                    sb.append(kx.name+" ,");
                sb.deleteCharAt(sb.length()-1);
                System.out.println(sb.toString());
            }
            else{
                System.out.println(sb.toString());
            }
        }
        // Just method to check if everything is parsed
        if((MethodsL.size() + InterventionsL.size() + OutcomesL.size() + ParticipantsL.size() + FilenameL.size()) != 0)
            System.out.println("ERROR : Something not parsed");        
                
                
        //Removed end of comment delimeter here
        //Remove this if you don't want Inner Keys
    }
    
    
    // Recursive method that prints the outer keys and Inner Keys and stores them in list "set"
    public static String iKeyValues(String g) {
        HashMap<String, String> KeyV = new HashMap<String, String>();
        List<String> all = new ArrayList<String>();
        //merge
        String ptr = g;
        g = merge(ptr);

        //prints
//        for(String g: testC){
//            System.out.println("\n\n ==== \n\n");
//            String[] tokens = g.split("<>");
//            for(String pqr : tokens)
//                System.out.println(pqr.trim());
//        }
        String[] tokens = g.split("<>");
        boolean bigDot = false;
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            String noParanthesis = str.replaceAll("\\(.+?\\)", "");
            if (noParanthesis.contains("•")) {
                bigDot = true;
            }
            if (!noParanthesis.matches("//s*")) {
                if (bigDot) {
                    if (str.contains("•")) {
                        str = str.replaceAll("•", "");
                        if (str.contains(":")) {
                            String[] inner = str.split(":");
                            if (inner.length >= 2) {
                                all.add("#Key");
                                all.add(str.substring(0, str.indexOf(":")));
                                all.add("#Value");
                                all.add(str.substring(str.indexOf(":") + 1, str.length()));
                            } else if (inner.length == 1) {
                                all.add("#Key");
                                all.add(str.trim());
                                all.add("#Value");
                            }
                        } else {
                            all.add("#Key");
                            all.add(str.trim());
                            all.add("#Value");
                        }
                    } else {
                        String[] inner = str.split(":");
                        if (str.contains(":") && (inner.length == 1)) {
//                                String[] inner = str.split(":");
//                                if (inner.length >= 2) {
//                                    System.out.println("Key-" + str.substring(0, str.indexOf(":")));
//                                    System.out.println("Value-" + str.substring(str.indexOf(":") + 1, str.length()));
//                                } else if (inner.length == 1) {
                            all.add("#Key");
                            all.add(str.trim());
                            all.add("#Value");
//                                }
                        } else {
                            all.add(str.trim());
                        }
                    }

                } else if (noParanthesis.contains(":")) {
                    String[] inner = str.split(":");
                    if (inner.length >= 2) {
                        all.add("#Key");
                        all.add(str.substring(0, str.indexOf(":")));
                        all.add("#Value");
                        all.add(str.substring(str.indexOf(":") + 1, str.length()));
                    } else if (inner.length == 1) {
                        all.add("#Key");
                        all.add(str.trim());
                        all.add("#Value");
                    }
                } else {
                    all.add(str.trim());
                }
            }

        }
        
        int i = 0;
        while (i < all.size()) {
            String str = all.get(i);
            if (str.contains("#Key")) {
                StringBuilder Kb = new StringBuilder();
                StringBuilder Vb = new StringBuilder();
                i++;
                if (i < all.size()) {
                    str = all.get(i);
                }
                if (!str.contains("#Value")) {
                    Kb.append(str);
                    i++;
                }
                if (i < all.size()) {
                    str = all.get(i);
                }
                if (str.contains("#Value")) {
                    i++;
                    if (i < all.size()) {
                        str = all.get(i);
                    }
                    while ((!str.contains("#Key")) && (i < all.size())) {
                        Vb.append(str);
                        Vb.append("<>");
                        i++;
                        if (i < all.size()) {
                            str = all.get(i);
                        }
                    }
                    i--;
                }
                KeyV.put(Kb.toString(), Vb.toString());
            }
            i++;
        }
//        System.out.println("==== Map Print ====");
        if(KeyV.isEmpty()){
            if(g.contains("<>")){
                g = g.replaceAll("<>", "");
            }
            return g;
        }
            
        for (Map.Entry entry : KeyV.entrySet()) {
//            System.out.println(entry.getKey() + "\t" + entry.getValue());
            set.add("Kee\t"+entry.getKey());
            set.add("Valyou\t"+iKeyValues(entry.getValue().toString()));
//            System.out.println("Key\t"+entry.getKey() );
//            System.out.println("Value\t"+fillValues(entry.getValue().toString()));
        }
//        System.out.println("==== END ====");
        return "";
    }
    //prints the given list into a proper format
    public static void printIK(){
        List<String> outerKey= new ArrayList<String>();
        for(String str : set){
            if(!str.matches("//s*")){
                String[] tokens = str.split("\t");
                if (str.contains("Kee") && tokens.length == 2) {
                    outerKey.add(tokens[1]);
                }
                else if (str.contains("Valyou")) {
                    if (tokens.length == 2) {
                        for (String ok : outerKey) {
                            System.out.print(ok + "\t");
                        }
                        System.out.print(tokens[1] + "\n");
                        if(outerKey.size() >= 1)
                            outerKey.remove(outerKey.size() - 1);
                    }
                    if (tokens.length == 1) {
                        if(outerKey.size() >= 1)
                            outerKey.remove(outerKey.size() - 1);
                    }
                }
            }
            
        }
    }
    
    
    
    //Print Keys and also adds them to list
    public static void printFreqMap(String cat, Map<String,Integer> keyF,List<Key> temp){
        System.out.println("Key\tFrequency\tCategory");
        for (Map.Entry entry : keyF.entrySet()) {
            String k = entry.getKey().toString();
            Integer fq = Integer.parseInt(entry.getValue().toString());
//            System.out.println(k + "\t" + fq+"\t"+cat);
            if(fq > 2){
                Key kx = new Key(k, Integer.parseInt(entry.getValue().toString()), cat, countWords(k));
                temp.add(kx);
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
    
    public static List<String> getValues(String lookFor,String jsonText) throws ParseException{
        List<String> tempL = new ArrayList<String>();
        JSONParser parser = new JSONParser();
        KeyFinder finder = new KeyFinder();
        finder.setMatchKey(lookFor);
        while (!finder.isEnd()) {
            parser.parse(jsonText, finder, true);
            if (finder.isFound()) {
                finder.setFound(false);
                tempL.add((String) finder.getValue());
            }
        }
        System.out.println(lookFor +" Done");
        return tempL;
    }
    
    public static void printKeyValues(String g,Map<String,String> KeyV)  {


        //merge
        String ptr = g;
        g = merge(ptr);
        
        //prints
        
//        for(String g: testC){
//            System.out.println("\n\n ==== \n\n");
//            String[] tokens = g.split("<>");
//            for(String pqr : tokens)
//                System.out.println(pqr.trim());
//        }
        
        String[] tokens = g.split("<>");
        boolean bigDot = false;
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            if (str.contains("•")) {
                bigDot = true;
            }
            if (!str.matches("//s*")) {
                if (bigDot) {
                    if (str.contains("•")) {
                        str = str.replaceAll("•", "");
                        if (str.contains(":")) {
                            String[] inner = str.split(":");
                            if (inner.length >= 2) {
                                System.out.println("**Key**\t" + str.substring(0, str.indexOf(":")));
                                System.out.println("**Value**\t" + str.substring(str.indexOf(":") + 1, str.length()));
                            } else if (inner.length == 1) {
                                System.out.println("**Key**\t" + str.trim());
                                System.out.println("**Value**\t");
                            }
                        } else {
                            System.out.println("**Key**\t" + str.trim());
                            System.out.println("**Value**\t");
                        }
                    } else {
                        String[] inner = str.split(":");
                        if (str.contains(":") && (inner.length == 1)) {
//                                String[] inner = str.split(":");
//                                if (inner.length >= 2) {
//                                    System.out.println("**Key**\t" + str.substring(0, str.indexOf(":")));
//                                    System.out.println("**Value**\t" + str.substring(str.indexOf(":") + 1, str.length()));
//                                } else if (inner.length == 1) {
                            System.out.println("**Key**\t" + str.trim());
                            System.out.println("**Value**\t");
//                                }
                        } else {
                            System.out.println(str.trim());
                        }
                    }

                } else if (str.contains(":")) {
                    String[] inner = str.split(":");
                    if (inner.length >= 2) {
                        System.out.println("**Key**\t" + str.substring(0, str.indexOf(":")));
                        System.out.println("**Value**\t" + str.substring(str.indexOf(":") + 1, str.length()));
                    } else if (inner.length == 1) {
                        System.out.println("**Key**\t" + str.trim());
                        System.out.println("**Value**\t");
                    }
                } else {
                    System.out.println(str.trim());
                }
            }

        }

    }
    
    public static void fillValues(String g,Map<String,String> KeyV, String cat,Map<String,Integer> keyFreq) {
        
        List<String> all = new ArrayList<String>();
        //merge
        String ptr = g;
        g = merge(ptr);
        
        
        String[] tokens = g.split("<>");
        boolean bigDot = false;
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            if (str.contains("•")) {
                bigDot = true;
            }
            if (!str.matches("//s*")) {
                if (bigDot) {
                    if (str.contains("•")) {
                        str = str.replaceAll("•", "");
                        if (str.contains(":")) {
                            String[] inner = str.split(":");
                            if (inner.length >= 2) {
                                all.add("#Key");
                                all.add( str.substring(0, str.indexOf(":")));
                                all.add("#Value");
                                all.add(str.substring(str.indexOf(":") + 1, str.length()));
                            } else if (inner.length == 1) {
                                all.add("#Key");
                                all.add(str.trim());
                                all.add("#Value");
                            }
                        } else {
                            all.add("#Key");
                            all.add(str.trim());
                            all.add("#Value");
                        }
                    } else {
                        String[] inner = str.split(":");
                        if (str.contains(":") && (inner.length == 1)) {
//                                String[] inner = str.split(":");
//                                if (inner.length >= 2) {
//                                    System.out.println("Key-" + str.substring(0, str.indexOf(":")));
//                                    System.out.println("Value-" + str.substring(str.indexOf(":") + 1, str.length()));
//                                } else if (inner.length == 1) {
                            all.add("#Key");
                            all.add(str.trim());
                            all.add("#Value");
//                                }
                        } else {
                            all.add(str.trim());
                        }
                    }

                } else if (str.contains(":")) {
                    String[] inner = str.split(":");
                    if (inner.length >= 2) {
                        all.add("#Key");
                        all.add(str.substring(0, str.indexOf(":")));
                        all.add("#Value");
                        all.add(str.substring(str.indexOf(":") + 1, str.length()));
                    } else if (inner.length == 1) {
                        all.add("#Key");
                        all.add(str.trim());
                        all.add("#Value");
                    }
                } else {
                    all.add(str.trim());
                }
            }

        }
        int i =0;
        while(i < all.size()){
            String str = all.get(i);
            if(str.contains("#Key")){
                StringBuilder Kb = new StringBuilder();
                StringBuilder Vb = new StringBuilder();
                i++;
                if (i < all.size()) {
                    str = all.get(i);
                }
                if(!str.contains("#Value")){
                    Kb.append(str);
                    i++;
                }
                if (i < all.size()) {
                    str = all.get(i);
                }
                if(str.contains("#Value")){
                    i++;
                    if (i < all.size()) {
                        str = all.get(i);
                    }
                    while ((!str.contains("#Key")) && (i < all.size())) {
                        Vb.append(str);
                        i++;
                        if(i < all.size())
                            str = all.get(i);
                    }
                    i--;
                }
                KeyV.put(Kb.toString(), Vb.toString());
            }
            i++;
        }
//        System.out.println("==== Map Print ====");
        for (Map.Entry entry : KeyV.entrySet()) {
            String k = entry.getKey().toString();
            k = cleanKey(k);
            
            if(!k.matches("\\s*")){
                if (!entry.getValue().toString().matches("\\s*")){
                    System.out.println(k + "\t" + entry.getValue() + "\t" + cat);
                    keyFreq = addKey(keyFreq, k);
                    addValue(k,(String) entry.getValue(),cat);
                }
            }
                
        }
//        System.out.println("==== END ====");
    }
    
    public static Map<String,Integer> addKey(Map<String,Integer>  keyFreq,String x){
        Integer f = keyFreq.get(x);
        if(f == null){
            keyFreq.put(x, 1);
        }
        else{
            keyFreq.put(x, f+1);
        }
        return keyFreq;
    }
    
    public static void addValue(String k,String v,String cat) {
        Key f = kMap.get(k);
        if (f == null) {
            Key nK = new Key(k,cat);
            nK.valList.add(v);
            kMap.put(k, nK);
            
        } else {
            f.valList.add(v);
        }
        
    }
    
    public static String merge(String g){
        String[] tokens = g.split("<>");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            str = str.trim();

            
            
            if(!str.matches("//s*")){
                String qpr = str.replaceAll("\\(.+?\\)", "");
                if (qpr.trim().equalsIgnoreCase("Sex") || qpr.trim().equalsIgnoreCase("Age") || qpr.trim().equalsIgnoreCase("Overall")) {
                    str = "• " + str;
                }
                try {
                    if ((!str.substring(0, 1).toUpperCase().matches(str.substring(0, 1)))) {
                        sb.append(" " + str);
                    } else {
                        sb.append("<>" + str);
                    }
                } catch (Exception Ex) {
                    //System.out.println("*&Error&* "+str);
                    sb.append(" " + str);
                }
            }
                
        }
        return sb.toString();
        
    }
    
    public static String cleanKey(String ip){
        if(ip.trim().endsWith(":")){
            ip = ip.replaceFirst(":", "");
        }
        if(ip.contains(";")){
            ip.replaceAll(";", " ");
        }
        Matcher matcher;
        matcher = Pattern.compile("\\d\\.\\s+").matcher(ip);
        if (matcher.find()) {
            ip = ip.substring(3, ip.length());
        }
        ip = ip.trim();
        return ip;
    }
    
}
