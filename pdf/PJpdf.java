/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 *
 * @author Abhishek
 */
public class PJpdf {
    public static void main(String[] argsv) throws IOException{
        PDDocument pd;
        BufferedWriter wr;
        try {
            File input = new File("Huober-2012-Higher-efficacy-of-l.pdf");
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
        
        BufferedReader br = new BufferedReader(new FileReader(new File("SampleText.txt")));
        String line = "";
        while ((line = br.readLine()) != null) {
            
            if(isUTF8MisInterpreted(line)){
                System.out.println(line);
            }
        }
        
        
        
        
        
    }
    
    public static boolean isUTF8MisInterpreted(String input) {

        return isUTF8MisInterpreted(input, "US-ASCII");
    }

    public static boolean isUTF8MisInterpreted(String input, String encoding) {

        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        CharsetEncoder encoder = Charset.forName(encoding).newEncoder();
        ByteBuffer tmp;
        try {
            tmp = encoder.encode(CharBuffer.wrap(input));
        } catch (CharacterCodingException e) {
            return false;
        }

        try {
            decoder.decode(tmp);
            return true;
        } catch (CharacterCodingException e) {
            return false;
        }
    }
    
    
    
}


