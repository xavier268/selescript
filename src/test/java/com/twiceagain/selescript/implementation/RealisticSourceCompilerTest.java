/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import com.twiceagain.selescript.SSCompiler;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class RealisticSourceCompilerTest {
    
    
    @Test
    public void testCompilerInfrastructure() {
        String source = "!1+ 2;!(3+4);(!5)+6;";
        System.out.printf("%nTest-compiling : %s%n", source);
        SSCompiler cp = new SSCompiler(source);
        cp.printTreeString();
        
        String s = cp.compileToString(new SSListenerImplementation());
        if(cp.hasSyntaxError()) {
            fail(cp.getErrorMessage());
        };
        cp.compileToFile(new SSListenerImplementation());
        System.out.printf("%n%s%n",s);
    }
}
