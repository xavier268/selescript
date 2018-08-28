/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import auto.SelescriptListener;
import auto.SelescriptParser;
import com.twiceagain.selescript.Config;
import com.twiceagain.selescript.SSListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import org.antlr.v4.runtime.CharStream;

/**
 *
 * @author xavier
 */
public abstract class SSListenerSringVal extends SSListenerConstantExpression implements SSListener, SelescriptListener{

    public SSListenerSringVal(String s) {
        super(s);
    }

    public SSListenerSringVal(Path path) throws IOException {
        super(path);
    }

    public SSListenerSringVal(CharStream in) {
        super(in);
    }

    public void enterAt(SelescriptParser.AtContext ctx) {
    }

    public void enterBiid(SelescriptParser.BiidContext ctx) {
    }

    public void enterConcat(SelescriptParser.ConcatContext ctx) {
    }

    public void enterEq(SelescriptParser.EqContext ctx) {
    }

    public void enterId(SelescriptParser.IdContext ctx) {
    }

    public void enterNot(SelescriptParser.NotContext ctx) {
    }

    public void enterPar(SelescriptParser.ParContext ctx) {
    }

    public void enterSstring(SelescriptParser.SstringContext ctx) {
    }

    public void exitAt(SelescriptParser.AtContext ctx) {
    }

    public void exitBiid(SelescriptParser.BiidContext ctx) {
        isValidBiids(ctx.getText());
        prop.put(ctx, "BIID_" + ctx.getText().substring(1) + "()");
    }

    public void exitConcat(SelescriptParser.ConcatContext ctx) {
        String s = "plus(" + prop.get(ctx.stringval(0)) + "," + prop.get(ctx.stringval(1)) + ")";
        prop.put(ctx, s);
    }

    public void exitEq(SelescriptParser.EqContext ctx) {
        String s = "eq(" + prop.get(ctx.stringval(0)) + "," + prop.get(ctx.stringval(1)) + ")";
        prop.put(ctx, s);
    }

    public void exitId(SelescriptParser.IdContext ctx) {
        // isValidId(ctx.getText()); // Defaut to null (ie false) if not defined
        prop.put(ctx, "symtab.get(\"" + ctx.getText() + "\")");
    }

    public void exitNot(SelescriptParser.NotContext ctx) {
        String s = "not(" + prop.get(ctx.stringval()) + ")";
        prop.put(ctx, s);
    }

    public void exitPar(SelescriptParser.ParContext ctx) {
        prop.put(ctx, '(' + prop.get(ctx.stringval()) + ')');
    }

    /**
     * Stringval and above are  always quoted in their internel representation.
     * Constants below are never quoted.
     * @param ctx
     */
    public void exitSstring(SelescriptParser.SstringContext ctx) {
        String x = prop.get(ctx.constantstring());
        if (x != null) {
            x = Config.AP + x + Config.AP;
        }
        prop.put(ctx, x);
    }

    
}
