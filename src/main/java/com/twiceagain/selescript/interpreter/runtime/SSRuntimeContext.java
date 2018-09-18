/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.SSConfig;

/**
 * The class that maintains the context of the current running program. There
 * should be exactly one RuntimeContext per running script. It is created by providing
 * the existing SSConfig object.
 *
 * @author xavier
 */
public class SSRuntimeContext {
    
    private final SSConfig config ;

    public SSRuntimeContext(final SSConfig config) {
        this.config = config;

    }

    public boolean shouldStop() {
        return false;
    }

    public void requestStop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
