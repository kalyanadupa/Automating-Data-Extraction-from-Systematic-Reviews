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
                                    tempFN.stuL.get(i).method = new StringBuffer(nL).insert(nL.indexOf(find)+find.length(), "#$#").toString();
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
                                    tempFN.stuL.get(i).participants = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "#$#").toString();
                                
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
                                    tempFN.stuL.get(i).interventions = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "#$#").toString();
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
                                    tempFN.stuL.get(i).outcomes = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "#$#").toString();
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
                                tempFN.stuL.get(i).method = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "#$#").toString();
//                                System.out.println("newM "+ tempFN.stuL.get(i).method);
                            }
                        } else if(part){
                            String find = line;
                            String nL = tempFN.stuL.get(i).participants;
                            if (nL.contains(find)) {
                                tempFN.stuL.get(i).participants = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "#$#").toString();
                            }
                        } else if(out){
                            String find = line;
                            String nL = tempFN.stuL.get(i).outcomes;
                            if (nL.contains(find)) {
                                tempFN.stuL.get(i).outcomes = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "#$#").toString();
                            }
                        } else if (inter) {
                            String find = line;
                            String nL = tempFN.stuL.get(i).interventions;
                            if (nL.contains(find)) {
                                tempFN.stuL.get(i).interventions = new StringBuffer(nL).insert(nL.indexOf(find) + find.length(), "#$#").toString();
                            }
                        } 
                    }
                }
                br.close();
            }
            
            //tempFN.printFName();
        }
        
        // Printing DS
        
        for (fName tempFN : fnL) {
            tempFN.printFName();
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
    
    
}
