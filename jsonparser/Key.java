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
 * @author Abhishek
 */
public class Key {
    String name;
    int freq;
    String category;
    int words;
    public List<Key> gKey;
    String groupedKeys;
    
    public Key(String name1, int freq1, String category1,int words1){
        this.name = name1;
        this.freq = freq1;
        this.category = category1;
        this.words = words1;
        this.gKey = new ArrayList<Key>();
        
    }
    public Key(String name1, String category1,String groupedKeys1){
        this.name = name1;
        this.category = category1;
        this.groupedKeys =groupedKeys1;
    }
    public Key(String name1, String category1){
        this.name = name1;
        this.category = category1;
        this.groupedKeys = "";
    }
    public Key(String name1, int freq1){
        this.name = name1;
        this.freq = freq1;
        this.category = "";
        this.words = 0;
        this.gKey = new ArrayList<Key>();
        
    }
    public Key(String name1){
        this.name = name1;
        this.gKey = new ArrayList<Key>();
    }
}
