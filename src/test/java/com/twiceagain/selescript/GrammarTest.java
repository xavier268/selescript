/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import com.twiceagain.selescript.implementation.SSListener99Implementation;
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

    public Config config = new Config("example").setTargetPackage("com", "test");

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
        ok("go { } ");
        ok("");
        nok(";");

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
        ok(" TOTO + 2 + 3 ;");
        ok(" TOTO + 5 + ! 6 ;");
        ok(" TOTO + 1 + 5 + ! 6 ;");
        ok(" 3 + ! 5 + TOTO + 6 ; ");
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

    /**
     * Test compiling provided string for correctness.
     *
     * @param s
     */
    protected void ok(String s) {
        System.out.printf("\n----------\nOK -> %s", s);
        SSListener c = new SSListener99Implementation(s);
        c.printTreeString();
        if (c.hasSyntaxError()) {
            fail(c.getErrorMessage());
        }
    }

    /**
     * Test compiling provided string for correctness.
     *
     * @param s
     */
    protected void nok(String s) {
        System.out.printf("\n----------\nNOK -> %s", s);
        SSListener c = new SSListener99Implementation(s);
        if (!c.hasSyntaxError()) {
            c.printTreeString();
            fail("**This should have been detected as incorrect ??!!**");
        } else {
            System.out.printf("\nExpected error : %s", c.getErrorMessage());
            c.printTreeString();
        }
    }
}
