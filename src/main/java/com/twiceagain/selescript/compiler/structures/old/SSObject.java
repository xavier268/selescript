/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler.structures.old;

/**
 * All internal compiler objects iimplement that interface.
 * @author xavier
 */
@Deprecated
public interface SSObject {
    
    /**
     * Tell if this is true or false.
     * @return 
     */
    boolean toBoolean();
    
    /**
     * Generates the related java code.
     * @return 
     */
    default String toCode() { return "Code generation not implemented" ; }
    
}
