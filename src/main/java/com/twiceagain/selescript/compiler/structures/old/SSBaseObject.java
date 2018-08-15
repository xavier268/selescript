/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler.structures.old;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * A generic base object that build code frament as it moves.
 *
 * @author xavier
 */
@Deprecated
public class SSBaseObject implements SSObject {

    public StringBuilder code = new StringBuilder();
    
    public SSBaseObject(ParseTree node) {
        code.append(node.getText());
    }

    @Override
    public boolean toBoolean() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toCode() {
        return code.toString();
    }

    /**
     * Get the code fragment taht is being built.
     *
     * @return
     */
    public StringBuilder getCode() {
        return code;
    }

}
