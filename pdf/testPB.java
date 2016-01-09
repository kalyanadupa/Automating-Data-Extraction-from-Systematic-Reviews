/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.sun.xml.internal.ws.util.StringUtils;
import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import static pdf.PrintKeyValue.merge;

/**
 *
 * @author Abhishek
 */
public class testPB {
    static List<String> set= new ArrayList<String>();
    public static void main(String[] args) {
        
        HashMap<String, String> KeyV = new HashMap<String, String>();
        String px = "N: 3749 patients randomised (folic acid, vitamins B6 and B12: 937 versus folic acid,<> vitamin B12: 935 versus vitamin B6: 934 versus placebo: 943)<> Sex (% men)<> Folic acid, vitamins B6 and B12: 73%<> Folic acid, vitamin B12: 74%<> Vitamin for B6: 73%<> Placebo: 75%<> Age (mean ± SD, years)<> Folic acid, vitamins B6 and B12: 63.6 ± 11.9<> Folic acid, vitamin B12: 63.2 ± 11.6<> Vitamin B6: 62.5 ± 11.7<> (Continued) Placebo: 62.6 ± 11.4 years<> Inclusion criteria:<> Men and women aged 30 to 85 years,<> History of acute MI within seven days before randomisation.<> Exclusion criteria:<> Coexisting disease associated with a life expectancy < 4 years,<> Prescribed treatment with B vitamins or untreated vitamin B deficiency,<> Inability to follow the protocol, as judged by the investigator<>";
//        printKeyValues(px,KeyV);
        String g = "Elegibility: 5309.<> Randomised: 506 (254 vitamins versus 252 placebo).<> • Age (years)<> Overall: 61.4<> B-vitamins group: 61.7 (±10.1).<> Placebo group: 61.1 (± 9.6).<> • Sex (men):<> Overall: 61%<> B-vitamins group: 61%.<> Placebo group: 61%.<> • Inclusion criteria:<> 1. Men and postmenopausal women 40 years old<> 2. Fasting tHcy 8.5 mol/L<> 3. No clinical signs/symptoms of cardiovascular disease (CVD).<> • Exclusion criteria:<> 1. Fasting triglycerides > 5.64 mmol/L (500 mg/dL).<> 2. Diabetes mellitus or fasting serum glucose > 6.99 mmol/L (126 mg/dL).<> 3. Systolic blood pressure ≥ 160 mm Hg and/or diastolic blood pressure ≥ 100 mm<> Hg.<> 4. Untreated thyroid disease.<> 5. Creatinine clearance < 70 mL/min.<> 6. Life-threatening illness with prognosis 5 years.<> 7. Five alcoholic drinks daily.<>";
//        HashMap<String, String> KeyV = new HashMap<String, String>();
        fillValues(px);
        fillValues(g);
        for(String str : set){
            System.out.println(str);
        }
        printIK();

    }
    
    public static void printIK(){
        List<String> outerKey= new ArrayList<String>();
        for(String str : set){
//            System.out.println(str);
            String[] tokens = str.split("\t");
            if(str.contains("Kee")){
                outerKey.add(tokens[1]);
            }
            if(str.contains("Valyou")){
                if(tokens.length == 2){
                    for(String ok : outerKey)
                        System.out.print(ok+"\t");
                    System.out.print(tokens[1]+"\n");
                    outerKey.remove(outerKey.size()-1);
                }
                if(tokens.length == 1){
                    outerKey.remove(outerKey.size()-1);
                }
            }
        }
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
    
    public static String fillValues(String g) {
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
        //To Test
        /*
        for (String str : all) {
            System.out.println(str);
        }
                */
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
            set.add("Valyou\t"+fillValues(entry.getValue().toString()));
//            System.out.println("Key\t"+entry.getKey() );
//            System.out.println("Value\t"+fillValues(entry.getValue().toString()));
        }
//        System.out.println("==== END ====");
        return "";
    }
    
    public static String merge(String g){
        String[] tokens = g.split("<>");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            str = str.trim();
            String qpr = str.replaceAll("\\(.+?\\)", "");
            if(qpr.trim().equalsIgnoreCase("Sex")||qpr.trim().equalsIgnoreCase("Age"))
                str = "• "+str ;
//            System.out.println(str);
            
            try{
                if ((!str.substring(0, 1).toUpperCase().matches(str.substring(0, 1)))) {
                    sb.append(" " + str);
                } else {
                    sb.append("<>" + str);
                }
            }
            catch(PatternSyntaxException Ex){
                sb.append(" " + str);
            }
            catch(Exception Ex){
//                System.out.println("Err/" + str);
            }
                
        }
 
        return sb.toString();
        
    }
    
}

