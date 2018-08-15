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
 * @author xavier
 */
@Ignore
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
    public void testBasicWalkNumber() {
        
        SSCompiler cp = new SSCompiler("unit u { a = 23 + 5 ; } ");
        if(cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        };         
        cp.compile();
        if(cp.hasSyntaxError()) {
            fail(cp.errorListener.getFirstErrorMessage());
        }; 
    }
   
    
}
