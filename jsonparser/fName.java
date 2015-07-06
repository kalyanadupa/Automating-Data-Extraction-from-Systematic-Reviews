/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aka324
 */
public class fName {
    List<studies> stuL = new ArrayList<studies>();
    String fileName;
    
    public void printFName(){
        System.out.println("Filename: " + this.fileName); 
        for(studies st : stuL){
            //st.print();
            
            System.out.println("\n" + st.participants);
            st.parseParticipants();
            System.out.println("");
        }    
   }
}
