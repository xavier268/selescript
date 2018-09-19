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
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class SSVisitorConstantNumberTest {

    @Test
    public void test1() {
        assertEquals((Long) 3L,     t("1 + 2"));
        assertEquals((Long) 6L,     t("1 + 2 + 3"));
        assertEquals((Long) 7L,     t("1 + 2 * 3 "));
        assertEquals((Long) 5L,     t("1 * 2 + 3"));
        assertEquals((Long) (-1L),  t("1 - 2"));
        assertEquals((Long) 1L,     t("- 1 + 2"));
        assertEquals((Long) 0L,     t("1 / 2"));
        assertEquals((Long) 2L,     t("5 / 2 "));
        assertEquals((Long) (-2L),  t("1 + 2 - 5 "));
    }

    private Long t(String s) {
        SelescriptLexer lexer = new SelescriptLexer(CharStreams.fromString(s));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        SelescriptParser parser = new SelescriptParser(ts);
        parser.removeErrorListeners();
        parser.addErrorListener(ConsoleErrorListener.INSTANCE);
        SelescriptParser.ConstantnumberContext root = parser.constantnumber();
        return (Long) new SSVisitor(new SSRuntimeContext(new SSConfig())).visit(root);
    }

}
