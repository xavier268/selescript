/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import com.twiceagain.selescript.Config;
import com.twiceagain.selescript.SSListener;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class RealisticSourceCompilerTest {
    
    Config config = new Config("testClass").setTargetPackage("com","test");
    
    @Test
    public void testCompilerInfrastructure() {
        String source = "!1+ 2;!(3+4);(!5)+6;";
        System.out.printf("%nTest-compiling : %s%n", source);
        SSListener ls = new SSListenerImplementation(source);
        ls.compile(config);
        
        String s = ls.getCode();
        if(ls.hasSyntaxError()) {
            fail(ls.getErrorMessage());
        }
        ls.saveCode();
        System.out.printf("%n%s%n",s);
    }
}
