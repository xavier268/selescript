/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import auto.SelescriptLexer;
import auto.SelescriptParser;
import com.twiceagain.selescript.SSConfig;
import com.twiceagain.selescript.exceptions.SSNumberException;
import com.twiceagain.selescript.interpreter.runtime.SSRuntimeContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class SSVisitorStringvalTest {

    @Test
    public void test() {
        assertEquals("abc", t("'abc'"));
        assertEquals("abc", t("'a' + 'bc'"));
        assertEquals("abc", t("'a'+ 'b'+'c'"));

        assertEquals("123", t("100 ++ 23"));

        assertNull(t("toto"));
        assertNull(t("null"));

        assertNotNull(t("'abc' & 23"));
        assertNotNull(t("'abc' | 23"));
        assertNotNull(t("null | 23"));
        assertNotNull(t("'abc' | null"));
        assertNull(t("null | null"));
        assertNull(t("null & 22"));
        assertNull(t("22 & null"));

        assertNotNull(t("'abc' != null"));
        assertNotNull(t("'abc' == 'abc'"));
        assertNull(t("null != null"));
        assertNull(t("'ab' == 'abc'"));
        
        assertEquals("true",t("null == null"));
        assertEquals("abc",t("'abc' == 'ab' + 'c'"));
        
        

        // find
        assertEquals("cd", t("'abcdef' ~ 'cd' "));
        assertEquals("abcdef", t("'abcdef' ~ null "));
        assertEquals("", t("'abcdef' ~ '' "));
        assertEquals("a", t("'abcdef' ~ '^az*' "));
        assertEquals("abcdef", t("'abcdef' ~ '^a.*f$' "));

        assertEquals(null, t("null ~ 'kjh' "));
        assertEquals(null, t("null ~ null "));
        assertEquals(null, t("null ~ '' "));

        // Find & replace
        assertEquals("abxef", t("'abcdef' ~ 'cd' , 'x' "));
        assertEquals("abcdef", t("'abcdef' ~ null , 'x' "));
        assertEquals("abcdef", t("'abcdef' ~ null , null "));
        assertEquals("xaxbxcxdxexfx", t("'abcdef' ~ '' , 'x' "));
        assertEquals("abef", t("'abcdef' ~ 'cd' , null "));

        assertEquals("35", t("3 + 4 ++ 1 "));
        assertEquals("64", t("3*2 + 4  "));

        assertEquals(null, t("!'35'"));
        assertEquals(null, t("!35"));
        assertEquals("5", t("(!3)+5"));
        assertEquals(null, t("!3++5"));

        assertEquals("ab", t(" null + 'ab' "));
        assertEquals("ab", t("  'ab' + null "));

        assertNotNull(t("! null "));
    }

    @Test
    public void biidTest() {
        assertEquals("firefox", t("$browser"));
    }

    @Test
    public void testPrecedence() {
        assertEquals("7", t(" 1 ++ 2 * 3"));
        assertEquals("-1", t(" 1 ++ 2 * 3 - 8"));
        assertEquals("16", t("1 + 2*3"));

        assertEquals("7", t(" 1 ++ 20 / 3"));
        assertEquals("-1", t(" 1 ++ 20 / 3 - 8"));
        assertEquals("16", t("1 + 20/3"));
        
        assertEquals("537",t(" 1*2 ++ 3 + 5*6 ++ 7 == (53447 ~ 44 , ) "));
        assertEquals("537",t(" ( 1*2 ++ 3 + 5*6 ++ 7) == 537 "));
        assertEquals("537",t("   1*2 ++ 3 + 5*6 ++ 7  == 537 "));
        assertEquals(null,t(" 1*2 ++ 3 + 5*6 ++ 7 != 537 "));
        
   
    }

    @Test
    public void test1() {

        assertEquals("6", t("'1'++'3' ++ '2'"));
        assertEquals("6", t("1++'3' ++ 2"));
        assertEquals("6", t("'1'++3 ++ 2"));

        assertEquals("3", t("1 ++ 2"));
        assertEquals("6", t("1 ++ 2 ++ 3"));
        assertEquals("6", t("(1 ++ 2) ++ 3"));
        assertEquals("7", t("1 ++ 2 * 3 "));
        assertEquals("5", t("1 * 2 ++ 3"));
        assertEquals("-1", t("1 - 2"));
        assertEquals("1", t("- 1 ++ 2"));
        assertEquals("0", t("1 / 2"));
        assertEquals("2", t("5 / 2 "));
        assertEquals("12", t("5 / 2 ++ 10 "));
        assertEquals("-2", t("5 / -2 "));
        assertEquals("5", t("----5"));
        assertEquals("-2", t("1 ++ 2 - 5 "));
    }

    @Test
    public void test2() {

        assertEquals("12", t("3*4"));
        assertEquals("7", t("3 ++ 4"));

        assertEquals("35", t("'35'"));
        assertEquals("ab", t("'ab'"));
        assertEquals("35", t("\"35\""));
        assertEquals("ab", t("\"ab\""));

        assertNull(t("null"));

        assertEquals("kjh", t(" ('kjh')"));

    }

    @Test(expected = SSNumberException.class)
    public void test3() {
        t("10/0");
    }

    @Test(expected = SSNumberException.class)
    public void test4() {
        t("null ++ 23");
    }

    // =======================================================================
    private String t(String s) {
        SelescriptLexer lexer = new SelescriptLexer(CharStreams.fromString(s));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        SelescriptParser parser = new SelescriptParser(ts);
        parser.removeErrorListeners();
        parser.addErrorListener(new SSDebugErrorListener());
        SelescriptParser.StringvalContext root = parser.stringval();
        return (String) new SSVisitor(new SSRuntimeContext(new SSConfig())).visit(root);
    }

}
