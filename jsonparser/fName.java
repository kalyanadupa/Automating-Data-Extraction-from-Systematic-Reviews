/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author aka324
 */
public class fName {
    public List<studies> stuL = new ArrayList<studies>();
    public String fileName;
    
    public void printFName(){
        System.out.println("Filename: " + this.fileName); 
        for(studies st : stuL){
            st.print();
        }    
   }
    
    
    public void printAllKeys(HashMap<String, Integer> pKList,HashMap<String, Integer> iKList,HashMap<String, Integer> mKList,HashMap<String, Integer> oKList) {
        System.out.println("Filename: " + this.fileName);
        for (studies st : stuL) {
            st.getKeys(pKList, iKList, mKList, oKList);
        }
    } 
}
