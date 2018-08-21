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
 * Test listenr walk.
 *
 * @author xavier
 */
public class SSListnerTest {

    public SSListnerTest() {
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
    public void testBasicEmptyGoStatement() {

        SSCompiler cp = new SSCompiler("go{}");
        cp.printTreeString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        String result = cp.compile();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        System.out.printf(
                "\n=========================="
                + "\n%s"
                + "\n===================\n",
                result);
    }
@Test
    public void testBasicGo() {

        SSCompiler cp = new SSCompiler("go \"/html\" {  }");
        cp.printTreeString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        String result = cp.compile();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        System.out.printf(
                "\n=========================="
                + "\n%s"
                + "\n===================\n",
                result);
    }
}
