/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import com.twiceagain.selescript.exceptions.SSException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * Annoteted Tree. Will refuse to store/retrieve from null key. Stores the
 * compilation context such as weather Mongo or WebDriver need to be initialized. 
 *
 * @author xavier
 */
public class SSProperties extends ParseTreeProperty<String> {

    
    /**
     * Retrieves a code fragment.
     * @param node - the node to use
     * @return - the code fragment
     */
    @Override
    public String get(ParseTree node) {
        if (node == null) {
            throw new SSException("Trying to read property from null node");
        }
        return super.get(node);
    }

    /**
     * Remove attachements to this subtree/node.
     * @param node - subtree/node
     * @return What was removed.
     */
    @Override
    public String removeFrom(ParseTree node) {
        if (node == null) {
            throw new SSException("Trying to remove from null node");
        }
        return super.removeFrom(node);
    }

    /**
     * Attach a code fragment to a subtree/node.
     * @param node - subtree/node
     * @param s - code fragment to attach
     */
    @Override
    public void put(ParseTree node, String s) {
        if (node == null) {
            throw new SSException("Trying to put property to null node");
        }
        super.put(node, s);
    }

}
