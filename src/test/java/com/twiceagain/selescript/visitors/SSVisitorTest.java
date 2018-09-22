/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import com.twiceagain.selescript.exceptions.SSSyntaxException;
import static com.twiceagain.selescript.visitors.SSVisitorAbstract.trim1;
import static com.twiceagain.selescript.visitors.SSVisitorAbstract.trim2;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xavier
 */
public class SSVisitorTest {

    public SSVisitorTest() {
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

    @Test
    public void testTrim1() {
        assertEquals("abc", trim1("abc:"));
        assertEquals("a", trim1("a:"));

    }

    @Test(expected = SSSyntaxException.class)
    public void testTrim1a() {
        assertEquals(null, trim1(null));
    }

    @Test(expected = SSSyntaxException.class)
    public void testTrim1b() {
        assertEquals("", trim1(":"));
    }

}
