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
 *
 * @author xavier
 */
public class SSCompilerTest {

    public SSCompilerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUnit() {

        // Should work
        ok("unit u { } ");
        ok("unit u { 23 } ");
        ok("unit u { 23;5 } ");
        ok("unit u { ;; }; ");

        ok("unit u { page p {} }; ");
        ok("unit u { page p {page q {}} }; ");
        ok("unit u { page p {22 ; page q {25 }} }; ");

        // Should fail ...
        nok("unit { 23 }");
        nok("unit u { unit t {} }");

    }

    /**
     * Test compiling provided string for correctness.
     *
     * @param s
     * @return null if ok, else the error message.
     */
    protected void ok(String s) {
        System.out.printf("\nOk -> %s \n", s);
        SSErrorListener errorListener = new SSCompiler(s).errorListener;
        if (errorListener.isSyntaxError()) {
            fail(errorListener.getFirstErrorMessage());
        }        
    }
    
    /**
     * Test compiling provided string for correctness.
     *
     * @param s
     * @return null if ok, else the error message.
     */
    protected void nok(String s) {
        System.out.printf("\nNok -> %s \n", s);
        SSErrorListener errorListener = new SSCompiler(s).errorListener;
        if (! errorListener.isSyntaxError()) {
            fail("This should have been detected as incorrect ??!!");
        }        
    }
}
