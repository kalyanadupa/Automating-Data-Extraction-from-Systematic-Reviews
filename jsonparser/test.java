/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Abhishek
 */
public class test {
    
    public static void main(String argsv[]) throws FileNotFoundException, IOException{
        String str = "Ethnic: Chinese 100 patients (50 in shengmai group, M/F 30/20, mean age 58 years, duration of heart failure 4.8 years, heart function class 2/3/4: 10/35/5; 50 in control group, M/F 29/21, mean age 60 years, duration of heart failure 4.9 years, heart fuction class 2/3/4: 10/36/4) Setting: inpatients Diagnostic criteria: heart function class based NYHA class Exclusion criteria: not stated";
        if((str.toLowerCase().contains("inclusion criteria")) && (str.toLowerCase().contains("exclusion criteria"))){
            Matcher matcher;
            matcher = Pattern.compile("(Inclusion criteria|INCLUSION CRITERIA|inclusion criteria)\\s*(:|\\.)(.*)(Exclusion criteria|EXCLUSION CRITERIA|exclusion criteria)\\s*(:|\\.)(.*)").matcher(str);
            if(matcher.find()){
                System.out.println(matcher.group(3) + "\n\n\n" + matcher.group(6));
            }
        }
        else if(str.toLowerCase().contains("inclusion criteria")){                    
            Matcher matcher1;
            matcher1 = Pattern.compile("(Inclusion criteria|INCLUSION CRITERIA|inclusion criteria)\\s*(:|\\.)(.*)").matcher(str);
            if(matcher1.find()){
                System.out.println(matcher1.group(3));
            }
        }
        else if (str.toLowerCase().contains("exclusion criteria")) {
            Matcher matcher1;
            matcher1 = Pattern.compile("(Exclusion criteria|EXCLUSION CRITERIA|exclusion criteria)\\s*(:|\\.)(.*)").matcher(str);
            if (matcher1.find()) {
                System.out.println(matcher1.group(3));
            }
        }
    }
    
    
    
    
}
