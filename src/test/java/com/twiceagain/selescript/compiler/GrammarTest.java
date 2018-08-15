/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test grammar syntax.
 * @author xavier
 */
public class GrammarTest {

    public GrammarTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("\n---------\n");
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUnit() {

      
        ok("unit u { } ");
        nok("unit u { } ; ");        // final ; refused
        nok("unit u { } kjh ");      // final text refused
        nok("unit u { ; } ");       // empty statement rejected
        nok("unit  { } ");
        nok(" u { } ");
        
        
        ok("unit u { } ");
        
        
    }
    
    @Test
    public void testComments() {
        ok("unit u { } ");
        nok("unit u { /* khjkhk kjhk j /* } ");
        ok("unit u { /* khjkhk kjhk j  */ } ");
        ok("unit /* hh */ u { } ");
        ok("/* ////***/unit u {}");
        
        
    }

    /**
     * Test compiling provided string for correctness.
     *
     * @param s
     */
    protected void ok(String s) {
        System.out.printf("\n----------\nOK -> %s", s);
        SSCompiler c = new SSCompiler(s);
        c.printTreeString();
        if (c.hasSyntaxError()) {
            fail(c.errorListener.getFirstErrorMessage());
        }
    }

    /**
     * Test compiling provided string for correctness.
     *
     * @param s
     */
    protected void nok(String s) {
        System.out.printf("\n----------\nNOK -> %s", s);
        SSCompiler c = new SSCompiler(s);        
        if (!c.hasSyntaxError()) {
            c.printTreeString();
            fail("**This should have been detected as incorrect ??!!**");
        } else {
            System.out.printf("\nExpected error : %s", c.errorListener.getFirstErrorMessage());
            c.printTreeString();
        }
    }
}
