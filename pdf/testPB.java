/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 *
 * @author Abhishek
 */
public class testPB {
    
    public static void main(String[] args) {
       String line = "Methods Number of centres: 4";
       String find = line.substring(line.indexOf(" ")+1, line.length());
        
       String nL = "Randomised controlled trial (pilot study) Number of centres: 4 in the USA Dates enrolled: March 1996 to March 1997 Follow-up: 60 days";
       //str = new StringBuffer(str).insert(str.length()-2, ".").toString();
       nL = new StringBuffer(nL).insert(nL.indexOf(find)+find.length(), "#$#").toString();
       System.out.println(nL);
       //System.out.println(nL.substring(0, nL.lastIndexOf(find)+ find.length()) + "#$#"+ nL.substring(nL.lastIndexOf(find)+find.length()+1, nL.length()));
       
        PDDocument pd;
        BufferedWriter wr;
        try {
            File input = new File("pdfs/0076065818/Marti-Carvajal-2013-Homocysteine-lowerin.pdf");
            File output = new File("pdf1.txt");
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
    }
    
}
