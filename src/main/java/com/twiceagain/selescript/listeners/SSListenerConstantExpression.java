/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.listeners;

import auto.SelescriptParser;
import com.twiceagain.selescript.configuration.Config;
import com.twiceagain.selescript.configuration.SSListener;
import com.twiceagain.selescript.exceptions.SSNumberFormatException;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 *
 * @author xavier
 */
public class SSListenerConstantExpression extends SSBaseListener implements SSListener {

    public SSListenerConstantExpression(Config config, ParseTreeProperty<String> prop) {
        super(config, prop);
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
        try {
            Integer i = Integer.decode(prop.get(ctx.constantnumber()));
            prop.put(ctx, String.format("%d", -i));
        } catch (NumberFormatException ex) {
            throw new SSNumberFormatException("Trying to apply Uminus to " + ctx.constantnumber(), ex);
        }
    }

}
