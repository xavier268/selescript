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
import org.antlr.v4.runtime.ConsoleErrorListener;
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
        assertEquals("abc", t("'a' 'bc'"));
        assertEquals("abc", t("'a''b''c'"));

        assertEquals("123", t("100 + 23"));

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

    }

    private String t(String s) {
        SelescriptLexer lexer = new SelescriptLexer(CharStreams.fromString(s));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        SelescriptParser parser = new SelescriptParser(ts);
        parser.removeErrorListeners();
        parser.addErrorListener(ConsoleErrorListener.INSTANCE);
        SelescriptParser.StringvalContext root = parser.stringval();
        return (String) new SSVisitor(new SSRuntimeContext(new SSConfig())).visit(root);
    }
}
