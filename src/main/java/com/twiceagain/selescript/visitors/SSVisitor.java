/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import auto.SelescriptBaseVisitor;
import com.twiceagain.selescript.interpreter.runtime.SSRuntimeContext;

/**
 * Structure of a visitor. All specific visitor inherit from this structure.
 * Visitors are constructed with and return a RuntimeContext object.
 *
 * @author xavier
 */
public class SSVisitor extends SelescriptBaseVisitor<Object> {

    protected SSRuntimeContext rtc;
    
    public SSVisitor(SSRuntimeContext rtc) {
        this.rtc=rtc;
    }

    
    
}
