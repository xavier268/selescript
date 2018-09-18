/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter;

import auto.SelescriptLexer;
import auto.SelescriptParser;
import com.twiceagain.selescript.SSConfig;
import com.twiceagain.selescript.interpreter.runtime.SSRuntimeContext;
import com.twiceagain.selescript.visitors.SSUnitVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Main interpreter object. This object will create an executable tree for the
 * script to run,  then execute it.
 *
 * @author xavier
 */
public class SSInterpreter {    
    
    protected SelescriptParser parser;
    protected ParseTree root ;
    
    public SSInterpreter(CharStream in) {
        
        SelescriptLexer lexer = new SelescriptLexer(in);
        CommonTokenStream ts = new CommonTokenStream(lexer);
        parser = new SelescriptParser(ts);
        
        parser.removeErrorListeners();
        parser.addErrorListener(ConsoleErrorListener.INSTANCE);
        
        root = parser.unit();
        
        new SSUnitVisitor(new SSRuntimeContext(new SSConfig())).visit(root);
        
        
    }

}
