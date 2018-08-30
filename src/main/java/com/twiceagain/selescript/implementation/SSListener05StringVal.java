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
import org.antlr.v4.runtime.CharStream;

/**
 *
 * @author xavier
 */
public abstract class SSListener05StringVal extends SSListener02ConstantExpression implements SSListener, SelescriptListener{

    public SSListener05StringVal(String s) {
        super(s);
    }

    public SSListener05StringVal(Path path) throws IOException {
        super(path);
    }

    public SSListener05StringVal(CharStream in) {
        super(in);
    }

    @Override
    public void enterAt(SelescriptParser.AtContext ctx) {
    }

    @Override
    public void enterBiid(SelescriptParser.BiidContext ctx) {
    }

    @Override
    public void enterConcat(SelescriptParser.ConcatContext ctx) {
    }

    @Override
    public void enterEq(SelescriptParser.EqContext ctx) {
    }

    @Override
    public void enterId(SelescriptParser.IdContext ctx) {
    }

    @Override
    public void enterNot(SelescriptParser.NotContext ctx) {
    }

    @Override
    public void enterPar(SelescriptParser.ParContext ctx) {
    }

    @Override
    public void enterSstring(SelescriptParser.SstringContext ctx) {
    }

    @Override
    public void exitAt(SelescriptParser.AtContext ctx) {
        String s = "at(" + prop.get(ctx.stringval()) + ")";               
        prop.put(ctx, s);
    }

    @Override
    public void exitBiid(SelescriptParser.BiidContext ctx) {
        isValidBiids(ctx.getText());
        prop.put(ctx, "BIID_" + ctx.getText().substring(1) + "()");
    }

    @Override
    public void exitConcat(SelescriptParser.ConcatContext ctx) {
        String s = "plus(" + prop.get(ctx.stringval(0)) + "," + prop.get(ctx.stringval(1)) + ")";
        prop.put(ctx, s);
    }

    @Override
    public void exitEq(SelescriptParser.EqContext ctx) {
        String s = "eq(" + prop.get(ctx.stringval(0)) + "," + prop.get(ctx.stringval(1)) + ")";
        prop.put(ctx, s);
    }

    @Override
    public void exitId(SelescriptParser.IdContext ctx) {
        // isValidId(ctx.getText()); // Defaut to null (ie false) if not defined
        prop.put(ctx, "symtab.get(\"" + ctx.getText() + "\")");
    }

    @Override
    public void exitNot(SelescriptParser.NotContext ctx) {
        String s = "not(" + prop.get(ctx.stringval()) + ")";
        prop.put(ctx, s);
    }

    @Override
    public void exitPar(SelescriptParser.ParContext ctx) {
        prop.put(ctx, '(' + prop.get(ctx.stringval()) + ')');
    }

    /**
     * Stringval and above are  always quoted in their internel representation.
     * Constants below are never quoted.
     * @param ctx
     */
    @Override
    public void exitSstring(SelescriptParser.SstringContext ctx) {
        String x = prop.get(ctx.constantstring());
        if (x != null) {
            x = Config.AP + x + Config.AP;
        }
        prop.put(ctx, x);
    }

    
}
