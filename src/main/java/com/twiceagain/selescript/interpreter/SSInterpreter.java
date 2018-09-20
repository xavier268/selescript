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
import com.twiceagain.selescript.visitors.SSDebugErrorListener;
import com.twiceagain.selescript.visitors.SSVisitor;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final SSConfig config;

    /**
     * Interpret from a CharStream.
     *
     * @param config
     * @param in
     */
    public SSInterpreter(SSConfig config, CharStream in) {

        this.config = config;

        SelescriptLexer lexer = new SelescriptLexer(in);
        CommonTokenStream ts = new CommonTokenStream(lexer);
        parser = new SelescriptParser(ts);

        parser.removeErrorListeners();
        if (config.isDebug()) {
            parser.addErrorListener(new SSDebugErrorListener());
        } else {
            parser.addErrorListener(ConsoleErrorListener.INSTANCE);
        }

        root = parser.unit();

        new SSVisitor(new SSRuntimeContext(config)).visit(root);

    }

    /**
     * Interpret from the provided script in String format.
     *
     * @param config
     * @param script
     */
    public SSInterpreter(SSConfig config, String script) {
        this(config, CharStreams.fromString(script));
    }

    /**
     * Interpret from a script file given by its path.
     *
     * @param config
     * @param path
     * @throws java.io.IOException
     */
    public SSInterpreter(SSConfig config, Path path) throws IOException {
        this(config, CharStreams.fromPath(path));
    }

    /**
     * Use configuration to start the interpreter.Will throw if no sript file
     * was specified.
     *
     * @param config
     * @throws java.io.IOException
     */
    public SSInterpreter(SSConfig config) throws IOException {
        this(config, Paths.get(config.getScriptFileName()));
    }

}
