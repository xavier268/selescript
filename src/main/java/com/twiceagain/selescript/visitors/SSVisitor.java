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
public class SSVisitor extends SSVisitorAbstract {

    public SSVisitor(SSRuntimeContext rtc) {
        super(rtc);
    }

    @Override
    public Object visitUnit(UnitContext ctx) {
        System.out.println("Visiting unit !");        
        for(StatementContext s : ctx.statement()) {                  
            visit(s);        
            if(rtc.shouldStop() ) break;
        }        
        return null;
    }
    
    @Override
    public Object visitCheck(CheckContext ctx ) {
        System.out.println("Visiting check !");
        if(rtc.shouldStop()) return null;
        String s = (String) visit(ctx.stringval());
        return null;
    }
    
    // ===========================================
    //    Constant numbers represented as Long
    // ===========================================
    
    @Override
    public Long visitCnnumber(CnnumberContext ctx) {
       return Long.decode(ctx.NUMBER().getText());
    }
    
    @Override
    public Long visitCnuminus(CnuminusContext ctx) {
        return -(Long)visit(ctx.constantnumber());
    }
    
    @Override
    public Long visitCnminus(CnminusContext ctx) {
        return (Long)visit(ctx.constantnumber(0)) - (Long)visit(ctx.constantnumber(1));
    }
    @Override
    public Long visitCnplus(CnplusContext ctx) {
        return (Long)visit(ctx.constantnumber(0)) + (Long)visit(ctx.constantnumber(1));
    }
    
    @Override
    public Long visitCntimes(CntimesContext ctx) {
        return (Long)visit(ctx.constantnumber(0)) * (Long)visit(ctx.constantnumber(1));
    }
    
    @Override
    public Long visitCndiv(CndivContext ctx) {
        return (Long)visit(ctx.constantnumber(0)) / (Long)visit(ctx.constantnumber(1));
    }
    
    @Override
    public Long visitCnpar(CnparContext ctx) {
        return (Long) visit(ctx.constantnumber());
    }
    
    // ================================================
    // Constant strings represented as String
    // ================================================
    
    @Override
    public String visitCsstring(CsstringContext ctx) {
        return trim2(ctx.STRING().getText());       
    }
    
    @Override
    public String visitCsnumber(CsnumberContext ctx) {
        return "" + (Long) visit(ctx.constantnumber());
    }
    
    
}
