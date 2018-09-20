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
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.DiagnosticErrorListener;
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
        assertEquals("34", t("3 4"));
        assertEquals("35", t("3 4 + 1 "));
        assertEquals("64", t("3*2 4  "));
        
        assertEquals("35", t("'35'"));
        assertEquals("ab", t("'ab'"));
        assertEquals("35", t("\"35\""));
        assertEquals("ab", t("\"ab\""));
        
        assertEquals(null, t("!'35'"));
        assertEquals(null, t("!35"));
        assertEquals(null, t("!3+5"));
        
        assertEquals("ab", t(" null 'ab' "));
        assertEquals("ab", t("  'ab' null "));
        
        assertNotNull(t("! null "));
        assertNull(t("null"));
        
        assertEquals("kjh", t(" ('kjh')"));
        
    }
    
    private String t(String s) {
        SelescriptLexer lexer = new SelescriptLexer(CharStreams.fromString(s));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        SelescriptParser parser = new SelescriptParser(ts);
        parser.removeErrorListeners();
        parser.addErrorListener(new DebugErrorListener());
        SelescriptParser.ConstantstringContext root = parser.constantstring();
        return (String) new SSVisitor(new SSRuntimeContext(new SSConfig())).visit(root);
    }
}
