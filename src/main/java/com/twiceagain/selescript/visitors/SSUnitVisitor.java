/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import auto.SelescriptParser.*;
import com.twiceagain.selescript.interpreter.runtime.SSRuntimeContext;

/**
 *Visit a Unit (ie, the full script).
 * @author xavier
 */
public class SSUnitVisitor extends SSVisitor {

    public SSUnitVisitor(SSRuntimeContext rtc) {
        super(rtc);
    }

    @Override
    public Void visitUnit(UnitContext ctx) {
        System.out.println("Visiting unit !");        
        for(StatementContext s : ctx.statement()) {                  
            visit(s);        
            if(rtc.shouldStop() ) break;
        }        
        return null;
    }
    
    @Override
    public Void visitCheck(CheckContext ctx ) {
        System.out.println("Visiting check !");
        if(rtc.shouldStop()) return null;
        String s = (String) visit(ctx.stringval());
        if(s == null) rtc.requestStop();        
        return null;
    }
    
    
}
