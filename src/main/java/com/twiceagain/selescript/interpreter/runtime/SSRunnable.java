/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

/**
 * Base class for all executable object. These objects are created when
 * listening the tree. The last object is the final program to run itself.
 *
 * @author xavier
 */
public class SSRunnable implements Runnable {
    
    
    protected SSRuntimeContext rctx ;
    
    public SSRunnable(SSRuntimeContext context) {
        this.rctx = context;
    }
    
    @Override
    public void run() {
        // Default is to do nothing.
    }

}
