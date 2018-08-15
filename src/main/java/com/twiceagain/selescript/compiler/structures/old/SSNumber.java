/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler.structures.old;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * A number object.
 * @author xavier
 */
@Deprecated
public class SSNumber extends SSBaseObject {
    
    public SSNumber(ParseTree node) {
        super(node);
    } 
    
}
