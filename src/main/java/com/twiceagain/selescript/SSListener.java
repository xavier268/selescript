/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * The SSListener is the base object that can compile a source script to java.
 *
 * @author xavier
 */
public interface SSListener extends ParseTreeListener {

    /**
     * Compile the script with default configuration.
     */
    public void compile();

    /**
     * Compile the script with provided configuration.
     *
     * @param config
     */
    public void compile(Config config);

    public String getCode();

    public void saveCode();

    public boolean hasSyntaxError();

    public String getErrorMessage();

    @Deprecated
    public String getTreeString();

    @Deprecated
    default public void printTreeString() {
        System.out.printf("%n%s%n", getTreeString());
    }

    /**
     * For debugging.
     */

    public void dump();
    void dump(ParseTree node);
}
