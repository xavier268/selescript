/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import auto.SelescriptLexer;
import auto.SelescriptParser;
import com.twiceagain.selescript.Config;
import com.twiceagain.selescript.SSErrorListener;
import com.twiceagain.selescript.SSListener;
import com.twiceagain.selescript.exceptions.SSException;
import com.twiceagain.selescript.exceptions.SSUndefinedBuiltinException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Various utilities used by the listener implementation. Handles all the
 * loading/saving/compile tasks so that the implementation can concentrate on
 * the logic needed for compiling.
 *
 * @author xavier
 */
public class SSAbstractListener implements SSListener {

    /**
     * The set of IDs that have been defined so far.
     */
    protected Set<String> definedIds = new HashSet<>();
    /**
     * Locally cached builtin list.
     */
    protected Set<String> definedBuiltins ;
    /**
     * The annotated tree to store the code as we compile the tree.
     */
    protected final ParseTreeProperty<String> prop = new ParseTreeProperty<>();
    ;
    /**
     * The root node holding the main code, once compilation has happened.
     */
    protected ParseTree rootNode = null;
    /**
     * The custom error listener used during initialization.
     */
    protected final SSErrorListener errorListener = new SSErrorListener();
    /**
     * The parser created from the grammar of the selescript langage.
     */
    protected SelescriptParser parser;

    private static final Logger LOG = LoggerFactory.getLogger(SSAbstractListener.class);

    protected Config config = new Config();
    
    /**
     * String for the source script to be compiled.
     *
     * @param s
     */
    public SSAbstractListener(String s) {
        this(CharStreams.fromString(s));
    }

    /**
     * Path to the source file containing the script to be compiled.
     *
     * @param path
     * @throws IOException
     */
    public SSAbstractListener(Path path) throws IOException {
        this(CharStreams.fromPath(path));
    }

    /**
     * Compile from a source delivered via a CharStream.
     *
     * @param in
     */
    public SSAbstractListener(CharStream in) {

        LOG.info("Constructing a new SSListener for source script");

        Lexer lex = new SelescriptLexer(in);
        lex.removeErrorListeners();
        lex.addErrorListener(errorListener);
        TokenStream ts = new CommonTokenStream(lex);
        parser = new SelescriptParser(ts);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        rootNode = parser.unit();

        LOG.info("AST for the source selescript file generated.");
    }

    /**
     * This will generate the code by annotating the tree.You typically call
     * this immediately after constructing the lstener. You can call it only
     * once for a given listener. You can then use the generated code to get it,
     * or save it to file.
     *
     * @param config - the configuration to use for that compilation.
     */
    @Override
    public void compile(Config config) {
        this.config = config;
        compile();
    }

    /**
     * Compile with default configuration.
     */
    @Override
    public void compile() {  
        if (prop.get(rootNode) == null) {
            definedBuiltins = new HashSet<>(config.getBuiltinsList());
            new ParseTreeWalker().walk(this, rootNode);
        } else {
            throw new SSException("You can compile only once with the same Listener object");
        }
    }

    /**
     * Check if Id is already known.Throw exception if not..
     *
     * @param id
     * @return true or false. Do not throw.
     */
    protected boolean isValidId(String id) {
        if (definedIds.contains(id)) {
            return true;
        } else {
            LOG.warn(
                    "Unrecognized id : "
                    + id
                    + " . Recognized ids are : "
                    + definedIds.toString()
            );
            return false;
        }
    }

    /**
     * Check if built-in id provided is valid.Throw exception if not.
     *
     * @param biid
     * @return true or throws
     */
    protected boolean isValidBiids(String biid) {
        if (definedBuiltins.contains(biid)) {
            return true;
        }
        throw new SSUndefinedBuiltinException("Unrecognized built-in : "
                + biid
                + " . Recognized built-ins are : "
                + definedBuiltins.toString());
    }

    /**
     * The final code will be found as the attribute of the tree root. An
     * exception will be thrown if no code was compiled by this listener.
     *
     * @return
     */
    @Override
    public String getCode() {
        if (rootNode == null) {
            throw new SSException("Listener has never compiled anything ? - No code available");
        }
        return prop.get(rootNode);
    }
    
    @Override
    public void saveCode() {
        config.saveCode(getCode());
    }

    @Override
    public void visitTerminal(TerminalNode node) {
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
    }

    /**
     * Check if a syntax error was detected during compilation.
     *
     * @return
     */
    @Override
    public boolean hasSyntaxError() {
        return errorListener.isSyntaxError();
    }

    @Override
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

    @Override
    public String getTreeString() {
        return rootNode.toStringTree(parser);
    
    }

    

}
