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
public class SSVisitorConstantStringTest {

    @Test
    public void test() {

        assertEquals("12", t("3*4"));
        assertEquals("7", t("3 ++ 4"));

        assertEquals("35", t("'35'"));
        assertEquals("ab", t("'ab'"));
        assertEquals("35", t("\"35\""));
        assertEquals("ab", t("\"ab\""));

        assertNull(t("null"));

        assertEquals("kjh", t(" ('kjh')"));

    }

    private String t(String s) {
        SelescriptLexer lexer = new SelescriptLexer(CharStreams.fromString(s));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        SelescriptParser parser = new SelescriptParser(ts);
        parser.removeErrorListeners();
        parser.addErrorListener(new SSDebugErrorListener());
        SelescriptParser.ConstantstringContext root = parser.constantstring();
        return (String) new SSVisitor(new SSRuntimeContext(new SSConfig())).visit(root);
    }
}
