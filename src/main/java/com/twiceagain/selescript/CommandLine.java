/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import com.twiceagain.selescript.interpreter.SSInterpreter;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;

/**
 * Main command line entry point.
 * @author xavier
 */
public class CommandLine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {  
        
        System.out.println("Please, type script (Ctl-D to finish) :");
        new SSInterpreter(CharStreams.fromStream(System.in));
    }
    
}
