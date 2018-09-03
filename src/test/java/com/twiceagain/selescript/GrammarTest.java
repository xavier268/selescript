/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import com.twiceagain.selescript.compiler.SSCompiler;
import com.twiceagain.selescript.configuration.Config;
import com.twiceagain.selescript.exceptions.SSException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test grammar syntax.
 *
 * @author xavier
 */
public class GrammarTest {

    public Config config = new Config("example")
            .setTargetPackage("test", "grammar")
            .setDebugMode(false)
            .setDryRunFlag(true);

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
    public void testOk() {
        ok("go { } ");
    }

    @Test
    public void testNok() {
        nok("2");
    }

    @Test
    public void testComments() {
        ok("go  { } ");
        nok("go { /* khjkhk kjhk j /* } ");
        ok("go  { /* khjkhk kjhk j  */ } ");
        ok("go /* hh */  { } ");
        ok("/* ////***/go  {}");
    }

    @Test
    public void testStringval() {
        ok("TOTO + 2 + 3 ;");
        ok("TOTO + 5 + ! 6 ;");
        ok("TOTO + 1 + 5 + ! 6 ;");
        ok("3 + ! 5 + TOTO + 6 ; ");
        ok("1 + 2 + TOTO + 3 + 4 ;");
        nok("1 + 2 + TOTO + 3 + 4 ");
        nok("toto -tata;");
        nok("toto -654;");
        ok("toto + - 654;");
        nok("toto * tata;");
        nok("toto * 654;");
        nok("22 * tata;");
        ok("22 * 666;");
        ok("22 * - 666;");
        nok("22 * + 666;");
        ok("toto + tata;");
    }

    @Test
    public void testConstants() {
        ok("5;");
        ok("1+2;");
        ok("-(10);");
        ok("-11;");
        ok("1- 3;");
        ok("1-3;");
        ok("4--5;");
        ok("4-------5;");
        ok("6+-7;");
        nok("8-+9");
    }
    
    @Test
    public void testLiteralString() {
        ok(" \"normal string\" ; ");
        // ok(" \"one escape \\\" string\" ; ");
    }

    /**
     * Test compiling provided string for correctness.
     *
     * @param s
     */
    protected void ok(String s) {
        System.out.printf("%n%s\t===> OK", s);
        SSCompiler c = new SSCompiler(config, s);
        if (c.hasSyntaxError()) {
            c.dump();
            fail(c.getErrorMessage());
        }
    }

    /**
     * Test compiling provided string for correctness.
     *
     * @param s
     */
    protected void nok(String s) {
        System.out.printf("%n%s\t===> NOK", s);
        SSCompiler c;
        try {
            c = new SSCompiler(config, s);
        } catch (SSException ex) {
            System.out.printf("\tError as expected : %s", ex.getMessage());
            return;
        }
        c.dump();
        fail("\nAn error was expected compiling : " 
                + s 
                + "\nThe code above should not have compiled successfully ... but it did !\n");
    }
}
