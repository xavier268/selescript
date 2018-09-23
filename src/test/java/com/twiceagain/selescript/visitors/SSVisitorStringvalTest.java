/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import auto.SelescriptLexer;
import auto.SelescriptParser;
import com.twiceagain.selescript.SSConfig;
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

        // find
        assertEquals("true", t("'abcdef' ~ 'cd' "));
        assertEquals("true", t("'abcdef' ~ null "));
        assertEquals("true", t("'abcdef' ~ '' "));
        assertEquals("true", t("'abcdef' ~ '^az*' "));
        assertEquals("true", t("'abcdef' ~ '^a.*f$' "));

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
        assertEquals("5", t("!3+5"));
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
    public void testAt() {
        // Will only be tested at the statement level.
    }

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
