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
 * Annoteted Tree. Will refuse to store/retrieve from null key.
 *
 * @author xavier
 */
public class SSProperties extends ParseTreeProperty<String> {

    @Override
    public String get(ParseTree node) {
        if (node == null) {
            throw new SSException("Trying to read property from null node");
        }
        return super.get(node);
    }
    
    @Override
    public String removeFrom(ParseTree node) {
        if (node == null) {
            throw new SSException("Trying to remove from null node");
        }
        return super.removeFrom(node);
    }
   
    @Override
    public void put(ParseTree node, String s) {
        if (node == null) {
            throw new SSException("Trying to put property to null node");
        }
        super.put(node, s);
    }

}
