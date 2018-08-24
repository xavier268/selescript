/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * Defines what the SSCompiler can use. Interface is grammar indepedant.
 *
 * @author xavier
 */
public interface SSListener extends  ParseTreeListener {

    

    /**
     * Return the compiled code as a String.
     * @return 
     */
    public String getCode();
    
    /**
     * Save the compiled code to file.
     */
    default public void saveCode() {
        Config.saveCode(getCode());
    }
    


}
