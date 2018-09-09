/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import auto.SelescriptLexer;
import auto.SelescriptParser;
import com.twiceagain.selescript.configuration.Config;
import com.twiceagain.selescript.configuration.SSErrorListener;
import com.twiceagain.selescript.exceptions.SSException;
import com.twiceagain.selescript.exceptions.SSSyntaxException;
import com.twiceagain.selescript.listeners.SSListenerConstantExpression;
import com.twiceagain.selescript.listeners.SSListenerParam;
import com.twiceagain.selescript.listeners.SSListenerStatement;
import com.twiceagain.selescript.listeners.SSListenerStringVal;
import com.twiceagain.selescript.listeners.SSListenerUnit;
import java.io.IOException;
import java.nio.charset.Charset;
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
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * The Compiler is constructed with source input, asked to compile, and then can
 * return code or save it.
 *
 * @author xavier
 */
public final class SSCompiler {

    /**
     * The set of IDs that have been defined so far.
     */
    private final Set<String> definedIds = new HashSet<>();
    /**
     * Locally cached builtin list.
     */
    private Set<String> definedBuiltins;
    /**
     * The annotated tree to store the code as we compile the tree.
     */
    private final SSProperties prop = new SSProperties();

    /**
     * The root node holding the main code, once compilation has happened.
     */
    private ParseTree rootNode = null;
    /**
     * The custom error listener used during initialization.
     */
    private final SSErrorListener errorListener = new SSErrorListener();
    /**
     * The parser created from the grammar of the selescript langage.
     */
    private SelescriptParser parser;

    protected Config config;

    /**
     * Compile according to the provided configuration object.This is the
     * prefered way to go. The code is generated, but not saved, until the
     * compiler is requested to do so.
     *
     * @param conf
     */
    public SSCompiler(Config conf) {
        CharStream in;
        if (conf == null) {
            config = new Config();
        } else {
            config = conf;
        }
        if (config.getSourceFileName() != null) {
            try {
                // read from file ...
                in = CharStreams.fromFileName(config.getSourceFileName());
            } catch (IOException ex) {
                throw new SSException("Could not read from " + config.getSourceFileName(), ex);
            }

        } else {
            try {
                // read from stdin ..
                in = CharStreams.fromStream(System.in, Charset.forName("UTF-8"));
            } catch (IOException ex) {
                throw new SSException("Could not open stdin as a CharStream ?!", ex);
            }
        }
        // Do the actual compile.
        compile(in);
    }

    /**
     * Compile from String, mainly for debugging purpose. Creates its own
     * debug-friendly configuration : dryrun, debugmode.
     *
     * @param scriptCode
     */
    public SSCompiler(String scriptCode) {

        this(null, scriptCode);
    }

    /**
     * Compile from string, using the provide configuration. Dryrun will be
     * forced. If config is null, a debug/dryrun config is created.
     *
     * @param conf
     * @param scriptCode
     */
    public SSCompiler(Config conf, String scriptCode) {
        if (conf == null) {
            config = new Config().setDebugMode(true).setDryRunFlag(true);
        } else {
            config = conf.setDryRunFlag(true);
        }
        compile(CharStreams.fromString(scriptCode));

    }

    /**
     * Do the actual compilation.
     *
     * @param in
     */
    protected final void compile(CharStream in) {

        Lexer lex = new SelescriptLexer(in);
        lex.removeErrorListeners();
        lex.addErrorListener(errorListener);
        TokenStream ts = new CommonTokenStream(lex);
        parser = new SelescriptParser(ts);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        rootNode = parser.unit();

        if (hasSyntaxError()) {
            throw new SSSyntaxException(getErrorMessage());
        }

        // Now, let's walk the generated tree with the listeners - order matters !
        new ParseTreeWalker().walk(new SSListenerConstantExpression(config, prop), rootNode);
        new ParseTreeWalker().walk(new SSListenerStringVal(config, prop), rootNode);
        new ParseTreeWalker().walk(new SSListenerParam(config, prop), rootNode);
        new ParseTreeWalker().walk(new SSListenerStatement(config, prop), rootNode);
        new ParseTreeWalker().walk(new SSListenerUnit(config, prop), rootNode);

        if (config.getDebugMode()) {
            dump();
        }

        if (hasSyntaxError()) {
            throw new SSException(getErrorMessage());
        }

    }

    /**
     * The final code will be found as the attribute of the tree root.
     *
     * @return
     */
    public String getCode() {
        if (rootNode == null) {
            throw new SSException("There is no parse tree - no code available ! ");
        }
        return prop.get(rootNode);
    }

    /**
     * Retun a hash of the generated code, excluding the variable properties
     * (time dependant, ...) so that this value is expected to stay the same
     * accross successive compilations. In practice, anything happening BEFORE
     * the first occurence of 'public void scrap' is ignored.
     *
     * @return
     */
    public String getCodeHash() {
        String c = getCode().split("public void scrap()", 2)[1];
        return config.getMd5Hash(c);
    }

    /**
     * Ask the config obejct to save the code.
     */
    public void saveCode() {
        config.saveCode(getCode());
    }

    /**
     * Check if a syntax error was detected during compilation.
     *
     * @return
     */
    public boolean hasSyntaxError() {
        return errorListener.isSyntaxError();
    }

    /**
     * Get the first (there can be more) error message.
     *
     * @return
     */
    public String getErrorMessage() {
        if (!hasSyntaxError()) {
            return "no error";
        } else {
            return "line " + errorListener.getFirstErrorLine()
                    + ", position "
                    + errorListener.getFirstErrorCharPositionInLine()
                    + " : " + errorListener.getFirstErrorMessage();
        }
    }

    /**
     * Get the parsed tree (for debugging).
     *
     * @return
     */
    public String getTreeString() {
        return rootNode.toStringTree(parser);

    }

    /**
     * Dumps the full annotated tree.
     */
    public void dump() {
        dump(rootNode);
    }

    /**
     * Dumps the annotated tree or sub-tree.
     *
     * @param node
     */
    public void dump(ParseTree node) {

        new ParseTreeWalker().walk(new ParseTreeListener() {
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
                System.out.printf("%nDUMP %s : %s %n===>%s<===%n", ctx.getText(), ctx.toStringTree(parser), prop.get(ctx));

            }
        }, node);
    }

}
