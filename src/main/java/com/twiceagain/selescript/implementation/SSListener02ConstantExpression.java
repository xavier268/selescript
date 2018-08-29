/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import auto.SelescriptListener;
import auto.SelescriptParser;
import com.twiceagain.selescript.SSListener;
import java.io.IOException;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;

/**
 *
 * @author xavier
 */
public abstract class SSListener02ConstantExpression extends SSAbstractListener implements SSListener, SelescriptListener{

    public SSListener02ConstantExpression(String s) {
        super(s);
    }

    public SSListener02ConstantExpression(Path path) throws IOException {
        super(path);
    }

    public SSListener02ConstantExpression(CharStream in) {
        super(in);
    }

    public void enterCdiv(SelescriptParser.CdivContext ctx) {
    }

    public void enterCminus(SelescriptParser.CminusContext ctx) {
    }

    public void enterCnumber(SelescriptParser.CnumberContext ctx) {
    }

    public void enterCpar(SelescriptParser.CparContext ctx) {
    }

    public void enterCplus(SelescriptParser.CplusContext ctx) {
    }

    public void enterCsc(SelescriptParser.CscContext ctx) {
    }

    public void enterCsnot(SelescriptParser.CsnotContext ctx) {
    }

    public void enterCspar(SelescriptParser.CsparContext ctx) {
    }

    public void enterCsplus(SelescriptParser.CsplusContext ctx) {
    }

    public void enterCsstring(SelescriptParser.CsstringContext ctx) {
    }

    public void enterCtimes(SelescriptParser.CtimesContext ctx) {
    }

    public void enterUminus(SelescriptParser.UminusContext ctx) {
    }

    public void exitCdiv(SelescriptParser.CdivContext ctx) {
        Integer i0 = Integer.decode(prop.get(ctx.constantnumber(0)));
        Integer i1 = Integer.decode(prop.get(ctx.constantnumber(1)));
        prop.put(ctx, String.format("%d", i0 / i1));
    }

    public void exitCminus(SelescriptParser.CminusContext ctx) {
        Integer i0 = Integer.decode(prop.get(ctx.constantnumber(0)));
        Integer i1 = Integer.decode(prop.get(ctx.constantnumber(1)));
        prop.put(ctx, String.format("%d", i0 - i1));
    }

    public void exitCnumber(SelescriptParser.CnumberContext ctx) {
        prop.put(ctx, ctx.NUMBER().getText());
    }

    public void exitCpar(SelescriptParser.CparContext ctx) {
        prop.put(ctx, prop.get(ctx.constantnumber()));
    }

    public void exitCplus(SelescriptParser.CplusContext ctx) {
        Integer i0 = Integer.decode(prop.get(ctx.constantnumber(0)));
        Integer i1 = Integer.decode(prop.get(ctx.constantnumber(1)));
        prop.put(ctx, String.format("%d", i0 + i1));
    }

    public void exitCsc(SelescriptParser.CscContext ctx) {
        prop.put(ctx, prop.get(ctx.constantnumber()));
    }

    public void exitCsnot(SelescriptParser.CsnotContext ctx) {
        if (prop.get(ctx.constantstring()) == null) {
            prop.put(ctx, "");
        }
    }

    public void exitCspar(SelescriptParser.CsparContext ctx) {
        prop.put(ctx, prop.get(ctx.constantstring()));
    }

    public void exitCsplus(SelescriptParser.CsplusContext ctx) {
        String r = null;
        String s0 = prop.get(ctx.constantstring(0));
        String s1 = prop.get(ctx.constantstring(1));
        if (s0 == null) {
            prop.put(ctx, s1);
            return;
        }
        if (s1 == null) {
            prop.put(ctx, s0);
            return;
        }
        prop.put(ctx, s0 + s1);
    }

    public void exitCsstring(SelescriptParser.CsstringContext ctx) {
        prop.put(ctx, trim2(ctx.getText()));
    }

    public void exitCtimes(SelescriptParser.CtimesContext ctx) {
        Integer i0 = Integer.decode(prop.get(ctx.constantnumber(0)));
        Integer i1 = Integer.decode(prop.get(ctx.constantnumber(1)));
        prop.put(ctx, String.format("%d", i0 * i1));
    }

    public void exitUminus(SelescriptParser.UminusContext ctx) {
        Integer i = Integer.decode(prop.get(ctx.constantnumber()));
        prop.put(ctx, String.format("%d", -i));
    }
    
    
    
}
