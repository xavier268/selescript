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
import com.twiceagain.selescript.visitors.SSVisitor;
import java.io.IOException;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Main interpreter object. This object will create an executable tree for the
 * script to run, then execute it.
 *
 * @author xavier
 */
public class SSInterpreter {

    private final SelescriptParser parser;
    private final ParseTree root;

    /**
     * Interpret from a CharStream.
     *
     * @param in
     */
    public SSInterpreter(CharStream in) {

        SelescriptLexer lexer = new SelescriptLexer(in);
        CommonTokenStream ts = new CommonTokenStream(lexer);
        parser = new SelescriptParser(ts);

        parser.removeErrorListeners();
        parser.addErrorListener(ConsoleErrorListener.INSTANCE);

        root = parser.unit();

        new SSVisitor(new SSRuntimeContext(new SSConfig())).visit(root);

    }

    /**
     * Interpret from the provided script in String format.
     *
     * @param script
     */
    public SSInterpreter(String script) {
        this(CharStreams.fromString(script));
    }

    /**
     * Interpret from a script file given by its path.
     *
     * @param path
     * @throws java.io.IOException
     */
    public SSInterpreter(Path path) throws IOException {
        this(CharStreams.fromPath(path));
    }

}
