/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import com.twiceagain.selescript.compiler.SSCompiler;

/**
 * 
 * @author xavier
 */
public class RunCompiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        new SSCompiler("unit testunit { nothing }").printTreeString();
        
        
    }
}
