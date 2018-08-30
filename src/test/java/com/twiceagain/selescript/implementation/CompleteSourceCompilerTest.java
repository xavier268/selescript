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
        SSListener99Implementation ll = new SSListener99Implementation("");
        String s = "abcdef";
        assertEquals("bcdef", ll.trim1(s));
        assertEquals("bcde", ll.trim2(s));

    }

    @Test
    public void testCompilerInfrastructure() {
        String source
                = "1; @ 2;"
                + "go toto { 12 ; titi = @ toto ;"
                + "go titi {"
                + "22;"
                + "23;"
                + "}"
                + "15;"
                + " }"               
                  + "5;";
        
        System.out.printf("%nTest-compiling : %s%n", source);
        SSListener ls = new SSListener99Implementation(source);
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
