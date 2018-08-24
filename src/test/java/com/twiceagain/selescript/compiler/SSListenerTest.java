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
import org.junit.Ignore;


/**
 * Test listenr walk.
 *
 * @author xavier
 */
public class SSListenerTest {

    public SSListenerTest() {
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
    @Ignore
    public void testBasicEmptyGoStatement() {

        SSCompiler cp = new SSCompiler("go{}");
        cp.printTreeString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        String result = cp.compileToString();
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
    @Ignore
    public void testBasicGo() {

        SSCompiler cp = new SSCompiler("go \"/html\" {  }");
        cp.printTreeString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        String result = cp.compileToString();
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
    @Ignore
    public void testBasicGoID() {

        SSCompiler cp = new SSCompiler("TOTO=\"toto\"; $url = TOTO; go TOTO {  } ");
        cp.printTreeString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        String result = cp.compileToString();
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
    @Ignore
    public void testBasicGoBiid() {

        SSCompiler cp = new SSCompiler("go $url {  }");
        cp.printTreeString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        String result = cp.compileToString();
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
    @Ignore
    public void testAssignEmit() {

        SSCompiler cp = new SSCompiler("go \"html\" { TOTO = \"toto\"; \"testing\"; emit TOTO,$url,\"simple string\" ; }");
        cp.printTreeString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        String result = cp.compileToString();
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
    public void testRealisticScrapper1() {
        SSCompiler cp = new SSCompiler(
                "go { "
                + "$url = \"http://www.google.com\"; "
                        +" go { "
                + "\".//div[@id='sfdiv']\"; "
                + "emit \".//input[@name='btnK']\"   ; "
                + "}}");
        cp.printTreeString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        String result = cp.compileToString();
        if (cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }
        System.out.printf(
                "\n=========================="
                + "\n%s"
                + "\n===================\n",
                result);
        
        System.out.println("And now, the same, saving to file ...");
        cp.compileToFile();
    }

}
