/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import jsonparser.Key;

/**
 *
 * @author Abhishek
 */
public class KeyPair {
    public Key k1;
    public Key k2;
    
    public KeyPair(Key k11, Key k22){
        this.k1 = k11;
        this.k2 = k22;
    }
    
}
