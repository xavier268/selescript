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

    @Override
    public void enterCdiv(SelescriptParser.CdivContext ctx) {
    }

    @Override
    public void enterCminus(SelescriptParser.CminusContext ctx) {
    }

    @Override
    public void enterCnumber(SelescriptParser.CnumberContext ctx) {
    }

    @Override
    public void enterCpar(SelescriptParser.CparContext ctx) {
    }

    @Override
    public void enterCplus(SelescriptParser.CplusContext ctx) {
    }

    @Override
    public void enterCsc(SelescriptParser.CscContext ctx) {
    }

    @Override
    public void enterCsnot(SelescriptParser.CsnotContext ctx) {
    }

    @Override
    public void enterCspar(SelescriptParser.CsparContext ctx) {
    }

    @Override
    public void enterCsplus(SelescriptParser.CsplusContext ctx) {
    }

    @Override
    public void enterCsstring(SelescriptParser.CsstringContext ctx) {
    }

    @Override
    public void enterCtimes(SelescriptParser.CtimesContext ctx) {
    }

    @Override
    public void enterUminus(SelescriptParser.UminusContext ctx) {
    }

    @Override
    public void exitCdiv(SelescriptParser.CdivContext ctx) {
        Integer i0 = Integer.decode(prop.get(ctx.constantnumber(0)));
        Integer i1 = Integer.decode(prop.get(ctx.constantnumber(1)));
        prop.put(ctx, String.format("%d", i0 / i1));
    }

    @Override
    public void exitCminus(SelescriptParser.CminusContext ctx) {
        Integer i0 = Integer.decode(prop.get(ctx.constantnumber(0)));
        Integer i1 = Integer.decode(prop.get(ctx.constantnumber(1)));
        prop.put(ctx, String.format("%d", i0 - i1));
    }

    @Override
    public void exitCnumber(SelescriptParser.CnumberContext ctx) {
        prop.put(ctx, ctx.NUMBER().getText());
    }

    @Override
    public void exitCpar(SelescriptParser.CparContext ctx) {
        prop.put(ctx, prop.get(ctx.constantnumber()));
    }

    @Override
    public void exitCplus(SelescriptParser.CplusContext ctx) {
        Integer i0 = Integer.decode(prop.get(ctx.constantnumber(0)));
        Integer i1 = Integer.decode(prop.get(ctx.constantnumber(1)));
        prop.put(ctx, String.format("%d", i0 + i1));
    }

    @Override
    public void exitCsc(SelescriptParser.CscContext ctx) {
        prop.put(ctx, prop.get(ctx.constantnumber()));
    }

    @Override
    public void exitCsnot(SelescriptParser.CsnotContext ctx) {
        if (prop.get(ctx.constantstring()) == null) {
            prop.put(ctx, "");
        }
    }

    @Override
    public void exitCspar(SelescriptParser.CsparContext ctx) {
        prop.put(ctx, prop.get(ctx.constantstring()));
    }

    @Override
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

    @Override
    public void exitCsstring(SelescriptParser.CsstringContext ctx) {
        prop.put(ctx, trim2(ctx.getText()));
    }

    @Override
    public void exitCtimes(SelescriptParser.CtimesContext ctx) {
        Integer i0 = Integer.decode(prop.get(ctx.constantnumber(0)));
        Integer i1 = Integer.decode(prop.get(ctx.constantnumber(1)));
        prop.put(ctx, String.format("%d", i0 * i1));
    }

    @Override
    public void exitUminus(SelescriptParser.UminusContext ctx) {
        Integer i = Integer.decode(prop.get(ctx.constantnumber()));
        prop.put(ctx, String.format("%d", -i));
    }
    
    
    
}
