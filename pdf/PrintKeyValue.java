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
    
    public static void main(String argsv[]) throws FileNotFoundException, IOException, ParseException{
        //results_cochrane_HF.json
        System.out.println("reading");
        String fileName = "results_cochrane_HF.json";
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
        
        int op = 0,ip = 0;
        
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
//                                System.out.println("** Error **");
//                                System.out.println("Find - " + find);
//                                System.out.println("nL - " + nL);
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
//                                System.out.println("** Error **");
//                                System.out.println("Find - " + find);
//                                System.out.println("nL - " + nL);
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
//                                System.out.println("** Error **");
//                                System.out.println("Find - " + find);
//                                System.out.println("nL - " + nL);
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
//                                System.out.println("** Error **");
//                                System.out.println("Find - " +find);                                
//                                System.out.println("nL - " +nL);                                
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
        
        // Printing in console
        /*
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
        
        */
        //Printing in DS
        for (fName tempFN : fnL) {
            //tempFN.printFName();
            for (int i = 0; i < tempFN.stuL.size(); i++) {
                fillValues(tempFN.stuL.get(i).participants, tempFN.stuL.get(i).pKeyV,"Participants");
                fillValues(tempFN.stuL.get(i).outcomes, tempFN.stuL.get(i).oKeyV,"Outcomes");
                fillValues(tempFN.stuL.get(i).method, tempFN.stuL.get(i).mKeyV,"Methods");
                fillValues(tempFN.stuL.get(i).interventions, tempFN.stuL.get(i).iKeyV,"Intervention");
            }
        }
        
        
        // Just method to check if everything is parsed
        if((MethodsL.size() + InterventionsL.size() + OutcomesL.size() + ParticipantsL.size() + FilenameL.size()) != 0)
            System.out.println("ERROR : Something not parsed");        
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
    
    public static void printKeyValues(String g,Map<String,String> KeyV) {


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
    
    public static void fillValues(String g,Map<String,String> KeyV, String cat) {
        
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
            if(entry.getKey().toString().trim().endsWith(":")){
                System.out.println(entry.getKey().toString().trim().replaceFirst(":", "") + "\t" + entry.getValue()+"\t"+cat);
            }
            else{
                System.out.println(entry.getKey().toString().trim() + "\t" + entry.getValue()+"\t"+cat);
            }
            
        }
//        System.out.println("==== END ====");
    }
    
    public static String merge(String g){
        String[] tokens = g.split("<>");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            str = str.trim();

            
            
            if(!str.matches("//s*")){
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
    
}
