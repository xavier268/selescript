/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

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

/**
 * This will generate ("compileToString") java code from a selecript scrapper
 * definition file or String. It is the main class to compile and generate
 * automatic java source code from selecrit code.
 *
 * @author xavier
 */
public class SSCompiler {

    protected SelescriptParser parser;
    protected ParseTree tree;
    protected final SSErrorListener errorListener = new SSErrorListener();

    /**
     *
     * @param s Code to compileToString.
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
     * @param in : where the code to compileToString is read from ...
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

        // Add listener ... or compile later ...
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
     * Prints the tree as a string to stdin (for debugging).
     */
  
    public void printTreeString() {
        System.out.printf("\n%s", getTreeString());
    }

    /**
     * Check if a syntax error was detected during compilation.
     *
     * @return
     */
    public boolean hasSyntaxError() {
        return errorListener.isSyntaxError();
    }

    public String getErrorMessage() {
        if (!hasSyntaxError()) {
            return "no error";
        } else {
            return errorListener.getFirstErrorLine()
                    + ":"
                    + errorListener.getFirstErrorCharPostionInLine()
                    + " -- " + errorListener.getFirstErrorMessage();
        }
    }

    /**
     * Walk the tree with the provided listener, doing the actual "compilation"
     * from selecript to java source.
     *
     * @param listener
     * @return The generated code.
     */
    public String compileToString(SSListener listener) {
        listener.compile(tree);
        return listener.getCode();
    }

    /**
     * Do the actual "compilation" from selecript to java source, saving the
     * generated code to file.
     *
     * @param listener
     */
    public void compileToFile(SSListener listener) {

        listener.compile(tree);
        listener.saveCode();
    }

}
