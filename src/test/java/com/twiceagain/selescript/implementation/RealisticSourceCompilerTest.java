/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import com.twiceagain.selescript.SSCompiler;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class RealisticSourceCompilerTest {
    
    
    @Test
    public void testCompilerInfrastructure() {
        SSCompiler cp = new SSCompiler(" 2 ; ");
        // String s = "See file !";cp.compileToFile(new SSListenerImplementation());
        String s = cp.compileToString(new SSListenerImplementation());
        // cp.compileToFile(new SSListenerImplementation());
        assertFalse(cp.hasSyntaxError());
        System.out.printf("%n%s%n",s);
    }
}
