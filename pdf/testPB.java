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
    
    public static void main(String[] args) {
        HashMap<String, String> KeyV = new HashMap<String, String>();
        String px = "N: 3749 patients randomised (folic acid, vitamins B6 and B12: 937 versus folic acid,<> vitamin B12: 935 versus vitamin B6: 934 versus placebo: 943)<> Sex (% men)<> Folic acid, vitamins B6 and B12: 73%<> Folic acid, vitamin B12: 74%<> Vitamin for B6: 73%<> Placebo: 75%<> Age (mean ± SD, years)<> Folic acid, vitamins B6 and B12: 63.6 ± 11.9<> Folic acid, vitamin B12: 63.2 ± 11.6<> Vitamin B6: 62.5 ± 11.7<> (Continued) Placebo: 62.6 ± 11.4 years<> Inclusion criteria:<> Men and women aged 30 to 85 years,<> History of acute MI within seven days before randomisation.<> Exclusion criteria:<> Coexisting disease associated with a life expectancy < 4 years,<> Prescribed treatment with B vitamins or untreated vitamin B deficiency,<> Inability to follow the protocol, as judged by the investigator<>";
//        printKeyValues(px,KeyV);
        
        String g = "Elegibility: 5309.<> Randomised: 506 (254 vitamins versus 252 placebo).<> • Age (years)<> Overall: 61.4<> B-vitamins group: 61.7 (±10.1).<> Placebo group: 61.1 (± 9.6).<> • Sex (men):<> Overall: 61%<> B-vitamins group: 61%.<> Placebo group: 61%.<> • Inclusion criteria:<> 1. Men and postmenopausal women 40 years old<> 2. Fasting tHcy 8.5 mol/L<> 3. No clinical signs/symptoms of cardiovascular disease (CVD).<> • Exclusion criteria:<> 1. Fasting triglycerides > 5.64 mmol/L (500 mg/dL).<> 2. Diabetes mellitus or fasting serum glucose > 6.99 mmol/L (126 mg/dL).<> 3. Systolic blood pressure ≥ 160 mm Hg and/or diastolic blood pressure ≥ 100 mm<> Hg.<> 4. Untreated thyroid disease.<> 5. Creatinine clearance < 70 mL/min.<> 6. Life-threatening illness with prognosis 5 years.<> 7. Five alcoholic drinks daily.<>";
//        HashMap<String, String> KeyV = new HashMap<String, String>();
        fillValues(px);
        
/* 
        // all code         
        String x = "(years) \n hi hekk \n hosls";
//        if ((!x.substring(0, 1).toUpperCase().matches(x.substring(0, 1)))) {
//            System.out.println(" " + x);
//        } else {
//            System.out.println("<>" + x);
//        }

//        String[] testC = {"Elegibility: 5309.<> Randomised: 506 (254 vitamins versus 252 placebo).<> • Age (years)<> Overall: 61.4<> B-vitamins group: 61.7 (±10.1).<> Placebo group: 61.1 (± 9.6).<> • Sex (men):<> Overall: 61%<> B-vitamins group: 61%.<> Placebo group: 61%.<> • Inclusion criteria:<> 1. Men and postmenopausal women 40 years old<> 2. Fasting tHcy 8.5 mol/L<> 3. No clinical signs/symptoms of cardiovascular disease (CVD).<> • Exclusion criteria:<> 1. Fasting triglycerides > 5.64 mmol/L (500 mg/dL).<> 2. Diabetes mellitus or fasting serum glucose > 6.99 mmol/L (126 mg/dL).<> 3. Systolic blood pressure ≥ 160 mm Hg and/or diastolic blood pressure ≥ 100 mm<> Hg.<> 4. Untreated thyroid disease.<> 5. Creatinine clearance < 70 mL/min.<> 6. Life-threatening illness with prognosis 5 years.<> 7. Five alcoholic drinks daily.<>","Intervention:<> Multivitamin therapy with 2.5 mg of folic acid, 50 mg of vitamin B6, and 1 mg of<> vitamin B12 per day<> Control:<> Matching placebo daily<> Treatment duration: 5 years.<>","Primary outcome (composite):<> Death from cardiovascular causes, MI, stroke.<> Secondary outcomes:<> Total Ischaemic events (composite of death from cardiovascular causes, MI, stroke, hos-<> pitalisation for unstable angina, and revascularisation),<> Death from any cause,<> Hospitalisation for unstable angina or congestive heart failure,<> Revascularisation,<> Incidence and death for cancer.<> Other outcomes: transient ischaemic attacks, venous thromboembolic events, fractures<>","N: 5522 patients randomised (vitamin: 2758 versus group: 2764 patients)<> • Sex (% men): vitamin: 71.1% versus placebo: 72.4%.<> • Age (mean ± SD): vitamin: 68.8 ± 7.1 versus placebo: 68.9 ± 6.8<> • Homocysteine level at baseline: 12.2 μmol/L (1.6 mg/L) • Inclusion criteria:<> Men and women aged > 55 years,<> Hstory of vascular disease (coronary, cerebrovascular, or peripheral vascular) or diabetes<> and additional risk factors for atherosclerosis,<> Irrespective of their homocysteine levels, from countries with mandatory folate fortifi-<> cation of food (Canada and the United States) and countries without mandatory folate<> fortification (Brazil, western Europe, and Slovakia)<> Exclusion criteria:<> Patients taking vitamin supplements containing more than 0.2 mg of folic acid per day<>","Duration of intervention: one year<> Intervention: ’Education and Support’<> After discharge:<> Initial hour long face to face consultation with experienced cardiac nurse within two<> weeks of discharge using a teaching booklet (45% of these consultations took place in<> patient’s home, remainder in hospital clinic).<> Following this weekly telephone contact for four weeks, bi-weekly for eight weeks then<> monthly until one year<> Initial consultation covered five sequential care domains for chronic illness including:<> patient knowledge of illness; the relation between medication and illness; the relation<> between health behaviours and illness; knowledge of early signs and symptoms of decom-<> pensation, and where and when to obtain assistance. Follow up phone calls reinforced<> the five care domains but did not modify current regimens or provide recommendations<> about treatment. However the nurse could recommend that the patient consulted his/<> her physician when the patient’s condition deteriorated sharply or when the patient had<> problems, in order to help patients to understand when and how to seek and access care<> Comparison: usual care.<> All usual care treatments and services ordered by their physicians<>","N = 15, moderate to severe COPD, breathlessness MRC scale 4 and 5<> All patients: clinical and functional diagnosis of COPD with moderate to severe ventilatory impairment<> and incapacitating breathlessness (Medical Research Council scores 4 (“I stop for breath after walking 100<> yards or after a few minutes on the level”) or 5 (“I am too breathless to leave the house”).<> Age: intervention group 66.6 (7.7), control group 65.0 (5.4)<> FEV1 pred. %: intervention group 38.0 (9.6), late intervention group 39.5 (13.3)<> BMI: intervention group 24.8 (6.9), late intervention group 25.6 (8.8)<> INCLUSION CRITERIA: absence of associated locomotor or neurological conditions; disease stability<> as indicated by no change in medication dosage or exacerbation of symptoms in the preceding 4 weeks<>" };
//        String[] testC = {"Heart function improvement >1 class: 81% in Shengmai group<>; 55% in control group<> QT dispersion by electrocardiogram: before treatment: 79.19 +/- 21.98 in shengmai group, 77.68+/- 22.<> 12 in control group; after treatment: 48.12 +/- 23.22 in shengmai group, 64.48 +/- 49.42 in control<> group JT dispersion of electrocardiogram : before treatment: 76.13 +/- 22.73 in shengmai group, 75.00 +/- 23.<> 72 in control group; after treatment: 44.22 +/- 22.52 in shengmai group, 63.19 +/-18.31 in control group<> Outcomes were measured at the end of treatment<><>"};
        String[] testC = {"n = 66 patients in Home-based CR group (33 in brief exercise programme subgroup<> & 33 inextended subgroup); n = 61 patients in Centre-based CR group (31 in brief<> subgroup & 30 in extended subgroup); 100% uncomplicated acute MI; mean age 52<> (SD 9); 100% male<> Inclusion: Uncomplicated AMI (elevated serum creatinine kinase or oxaloacetic transami- anase, prolonged chest pain consistent with AMI, new Q waves or evolutionary ST<> changes in ECG)<> Exclusion: Unable to undertake exercise test, congestive heart failure, unstable angina<> pectoris, valvular heart disease, atrial fibrillation, bundle branch block, history of by-<> pass, stroke, orthopaedic abnormalities, peripheral vascular disease, chronic pulmonary<> obstructive disease, obesity<>"};
        //merge
        for(int i = 0; i < testC.length;i++){
            String str = testC[i];
            testC[i] = merge(str);
        }
        //prints
        for(String g: testC){
            System.out.println("\n\n ==== \n\n");
            String[] tokens = g.split("<>");
            for(String pqr : tokens)
                System.out.println(pqr.trim());
        }

        for(String g :testC){
            System.out.println("\n\n ==== \n\n");
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
                                    System.out.println("Key-" + str.substring(0, str.indexOf(":")));
                                    System.out.println("Value-" + str.substring(str.indexOf(":") + 1, str.length()));
                                } else if (inner.length == 1) {
                                    System.out.println("Key-" + str.trim());
                                    System.out.print("Value-");
                                }
                            }
                            else{
                                System.out.println("Key-" + str.trim());
                                System.out.print("Value-");
                            }
                        } else {
                            String[] inner = str.split(":");
                            if(str.contains(":") && (inner.length ==1)){
//                                String[] inner = str.split(":");
//                                if (inner.length >= 2) {
//                                    System.out.println("Key-" + str.substring(0, str.indexOf(":")));
//                                    System.out.println("Value-" + str.substring(str.indexOf(":") + 1, str.length()));
//                                } else if (inner.length == 1) {
                                    System.out.println("Key-" + str.trim());
                                    System.out.print("Value-");
//                                }
                            }
                            else 
                                System.out.println(str.trim());
                        }

                    } else if (str.contains(":")) {
                        String[] inner = str.split(":");
                        if (inner.length >= 2) {
                            System.out.println("Key-" + str.substring(0, str.indexOf(":")));
                            System.out.println("Value-" + str.substring(str.indexOf(":") + 1, str.length()));
                        }
                        else if(inner.length ==1){
                            System.out.println("Key-" + str.trim());
                            System.out.print("Value-");
                        }
                    }
                    else{
                        System.out.println(str.trim());
                    }
                }

            }
        }
        
        
      
*/        
        
        
        
        
        
        
        
        
        
        
//       String line = "Methods Number of centres: 4";
//       String find = line.substring(line.indexOf(" ")+1, line.length());
//        
//       String nL = "Randomised controlled trial (pilot study) Number of centres: 4 in the USA Dates enrolled: March 1996 to March 1997 Follow-up: 60 days";
//       //str = new StringBuffer(str).insert(str.length()-2, ".").toString();
//       nL = new StringBuffer(nL).insert(nL.indexOf(find)+find.length(), "#$#").toString();
//       System.out.println(nL);
//       //System.out.println(nL.substring(0, nL.lastIndexOf(find)+ find.length()) + "#$#"+ nL.substring(nL.lastIndexOf(find)+find.length()+1, nL.length()));
//       
//        PDDocument pd;
//        BufferedWriter wr;
//        try {
//            File input = new File("pdfs/0076065818/Marti-Carvajal-2013-Homocysteine-lowerin.pdf");
//            File output = new File("pdf1.txt");
//            pd = PDDocument.load(input);
//            PDFTextStripper stripper = new PDFTextStripper();
//            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
//            stripper.writeText(pd, wr);
//            if (pd != null) {
//                pd.close();
//            }
//            wr.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
            System.out.println("Key\t"+entry.getKey() );
            System.out.println("Value\t"+fillValues(entry.getValue().toString()));
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
