/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import com.twiceagain.selescript.Config;
import com.twiceagain.selescript.SSListener;
import com.twiceagain.selescript.exceptions.SSException;
import com.twiceagain.selescript.exceptions.SSUndefinedBuiltinException;
import java.util.HashSet;
import java.util.Set;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Various utilities used by the listener implementation.
 *
 * @author xavier
 */
public abstract class SSAbstractListener implements SSListener {

    /**
     * The set of IDs that have been defined so far.
     */
    protected Set<String> definedIds;
    /**
     * Locally cached builtin list.
     */
    protected Set<String> definedBuiltins;
    /**
     * The annotated tree to store the code as we compile the tree.
     */
    protected ParseTreeProperty<String> prop;
    /**
     * The root node holding the main code, once compilation has happened. Null
     * otherwise.
     */
    protected ParseTree rootNode;

    private static final Logger LOG = LoggerFactory.getLogger(SSAbstractListener.class);

    public SSAbstractListener() {
        reset();
    }

    /**
     * Full reset fo the listener.
     */
    @Override
    public final void reset() {
        LOG.info("Reinitializing the listener");
        prop = new ParseTreeProperty<>();
        rootNode = null;
        definedIds = new HashSet<>();
        definedBuiltins = new HashSet<>(Config.getBuiltinsList());
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
        if (Config.getBuiltinsList().contains(biid)) {
            return true;
        }
        throw new SSUndefinedBuiltinException("Unrecognized built-in : "
                + biid
                + " . Recognized built-ins are : "
                + Config.getBuiltinsList().toString());
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

    /**
     * Walk the provided tree with this listener.
     *
     * @param tree
     */
    @Override
    public void compile(ParseTree tree) {
        reset();
        new ParseTreeWalker().walk(this, tree);
        rootNode = tree;
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

}
