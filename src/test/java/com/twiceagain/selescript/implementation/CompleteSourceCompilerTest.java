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
public class CompleteSourceCompilerTest {

    Config config = new Config("example").setTargetPackage("com", "test");

    @Test
    public void trimTest() {
        SSListenerImplementation ll = new SSListenerImplementation("");
        String s = "abcdef";
        assertEquals("bcdef", ll.trim1(s));
        assertEquals("bcde", ll.trim2(s));

    }

    @Test
    public void testCompilerInfrastructure() {
        String source
                = "!1+ 2;"
                + "! \"titi\";"
                + "(!5)+6; "
                + "( ! \"tata\" ) + \"titi\";"
                + "\"tttt\" + \"tata\";"
                + "toto = 1+3;"
                + "titi = toto + 5;"
                + "titi;" ;
        
        System.out.printf("%nTest-compiling : %s%n", source);
        SSListener ls = new SSListenerImplementation(source);
        ls.compile(config);
        ls.dump();

        String s = ls.getCode();
        if (ls.hasSyntaxError()) {
            fail(ls.getErrorMessage());
        }
        ls.saveCode();
        // System.out.printf("%n%s%n", s);
    }
}
