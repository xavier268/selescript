/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import com.twiceagain.selescript.exceptions.SSSyntaxException;
import static com.twiceagain.selescript.visitors.SSVisitorAbstract.trim2;
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
public class SSVisitorTest {

    public SSVisitorTest() {
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

    @Test(expected = SSSyntaxException.class)
    public void testTrim2a() {
        assertEquals("a", trim2("a"));
    }

    @Test(expected = SSSyntaxException.class)
    public void testTrim2b() {
        assertEquals(null, trim2(null));
    }

    @Test
    public void testTrim2() {
        assertEquals("ab", trim2("cabd"));

    }

}
