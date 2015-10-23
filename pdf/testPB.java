/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

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
        PDDocument pd;
        BufferedWriter wr;
        try {
            File input = new File("multipdf.pdf");  // The PDF file from where you would like to extract
            File output = new File("SampleText.txt"); // The text file where you are going to store the extracted data
            pd = PDDocument.load(input);
            pd.save("CopyOfInvoice.pdf"); // Creates a copy called "CopyOfInvoice.pdf"
            PDFTextStripper stripper = new PDFTextStripper();
//            stripper.setStartPage(35); //Start extracting from page 3
//            stripper.setEndPage(36); //Extract till page 5
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
