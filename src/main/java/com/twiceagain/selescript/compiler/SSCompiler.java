/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import auto.SelescriptLexer;
import auto.SelescriptParser;
import java.io.IOException;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * This will generate java code from a selecript scrapper definition.
 *
 * @author xavier
 */
public class SSCompiler {

    protected SelescriptParser parser;
    protected ParseTree tree;
    protected final SSErrorListener errorListener = new SSErrorListener();

    /**
     *
     * @param s Code to compile.
     */
    public SSCompiler(String s) {
        this(CharStreams.fromString(s));
    }

    /**
     *
     * @param path : path to code to be compiled.
     * @throws IOException
     */
    public SSCompiler(Path path) throws IOException {
        this(CharStreams.fromPath(path));
    }

    /**
     *
     * @param in : where the code to compile is read from ...
     */
    public SSCompiler(CharStream in) {
        // define input
        // CharStream in = CharStreams.fromString(input);

        // Create  Lexer 
        Lexer lex = new SelescriptLexer(in);

        lex.removeErrorListeners();
        lex.addErrorListener(errorListener);

        // Generate TokenStream
        TokenStream ts = new CommonTokenStream(lex);

        // Generate parser
        parser = new SelescriptParser(ts);

        // Add listener ... or walk later ...
        // parser.addParseListener(new MyListener());
        // Remove previous error listener, and add mine.
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        // Parse the grammar, returning the full tree.
        tree = parser.unit();

    }

    /**
     * Get a printable version of the tree, mainly for debugging.
     *
     * @return
     */
    public String getTreeString() {
        return tree.toStringTree(parser);
    }

    /**
     * Prints the tree as a string to stin (for debugging).
     */
    public void printTreeString() {
        System.out.printf("\n%s", getTreeString());
    }

    public boolean hasSyntaxError() {
        return errorListener.isSyntaxError();
    }

    /**
     * Wal the tree with the provided listener.
     *
     * @param listenr
     */
     void compile(ParseTreeListener listenr) {
        new ParseTreeWalker().walk(listenr, tree);
    }
     

    /**
     * Apply all the relavant listeners and/or visitors in the correct order to
     * generate the final result (java code).
     *
     * @return The generated code.
     */
    public String compile() {

        SSListener listr1 = new SSListener();
        compile(listr1);

        return listr1.getCode();

    }
    
    public String compileAndSave() {
        SSListener listr1 = new SSListener();
        compile(listr1);
        listr1.saveCode();
        return listr1.getCode();
    }

    

}
