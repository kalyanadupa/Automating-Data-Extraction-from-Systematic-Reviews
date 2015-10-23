/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abhishek
 */
public class parsehtml {
    public static void main(String[] argsv) throws FileNotFoundException, IOException{
        List<dataElement> data = new ArrayList<dataElement>();
        BufferedReader br = new BufferedReader(new FileReader(new File("out1")));
        String line = "";
        boolean meth = false;
        boolean part = false;
        boolean inter = false;
        boolean out = false;
        boolean not = false;
        
        while ((line = br.readLine()) != null) {
            if (line.equalsIgnoreCase("Methods")) {
                meth = true;
                part = false;
                inter = false;
                out = false;
                not = false;
            } else if (line.equalsIgnoreCase("Participants")) {
                part = true;
                meth = false;
                inter = false;
                out = false;
                not = false;
            } else if (line.equalsIgnoreCase("Interventions")) {
                meth = false;
                part = false;
                inter = true;
                out = false;
                not = false;
            } else if (line.equalsIgnoreCase("Outcomes")) {
                meth = false;
                part = false;
                inter = false;
                out = true;
                not = false;
            } else if (line.equalsIgnoreCase("Notes")) {
                meth = false;
                part = false;
                inter = false;
                out = false;
                not = true;
            }
            
            if(meth){
                String x = line;
                String[] tokens = x.split(":");
                if(x.contains(":") && (tokens.length > 2)){
                    dataElement dE = new dataElement(x.substring(0, x.indexOf(":")),x.substring(x.indexOf(":")+1,x.length() ),"Methods");
                    
                }
            }
            
        }
        
    }
}
