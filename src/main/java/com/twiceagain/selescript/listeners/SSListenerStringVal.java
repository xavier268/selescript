/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.listeners;

import auto.SelescriptParser;
import com.twiceagain.selescript.compiler.SSProperties;
import com.twiceagain.selescript.configuration.Config;
import static com.twiceagain.selescript.configuration.Config.AP;
import com.twiceagain.selescript.configuration.SSListener;

/**
 * StringVal are string values (possibly null). They are stored internally as
 * valid fragments of java code, e.g., Strings are quoted.
 *
 * @author xavier
 */
public class SSListenerStringVal extends SSBaseListener implements SSListener {

    public SSListenerStringVal(Config config, SSProperties prop) {
        super(config, prop);
    }

    @Override
    public void exitAt(SelescriptParser.AtContext ctx) {
        prop.needsWebdriver = true;
        StringBuilder sb = new StringBuilder();
        sb.append("at(");
        if (ctx.ID() == null) {
            sb.append(ctx.ID());
        } else {
            sb
                    .append(AP)
                    .append(ctx.ID().getText())
                    .append(AP);
        }
        sb.append(",");
        if (ctx.stringval() == null) {
            sb.append(ctx.stringval());
        } else {
            sb.append(prop.get(ctx.stringval()));
        }
        sb.append(")");
        prop.put(ctx, sb.toString());
    }

    @Override
    public void exitBiid(SelescriptParser.BiidContext ctx) {
        isValidBiids(ctx.getText());
        prop.put(ctx, "_" + ctx.getText() + ".get()");
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
    public void exitNeq(SelescriptParser.NeqContext ctx) {
        String s = "neq(" + prop.get(ctx.stringval(0)) + "," + prop.get(ctx.stringval(1)) + ")";
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
     * Stringval and above are always quoted in their internel representation.
     * Constants below are never quoted. Double-quotes are escaped when
     * converting a constant to StringVal.
     *
     * @param ctx
     */
    @Override
    public void exitSstring(SelescriptParser.SstringContext ctx) {
        String x = prop.get(ctx.constantstring());
        if (x != null) {
            x = AP + x.replaceAll("\"", "\\\\\"") + AP;
        }
        prop.put(ctx, x);
    }

    @Override
    public void exitOr(SelescriptParser.OrContext ctx) {
        String s = "or(" + prop.get(ctx.stringval(0)) + "," + prop.get(ctx.stringval(1)) + ")";
        prop.put(ctx, s);
    }

    @Override
    public void exitAnd(SelescriptParser.AndContext ctx) {
        String s = "and(" + prop.get(ctx.stringval(0)) + "," + prop.get(ctx.stringval(1)) + ")";
        prop.put(ctx, s);
    }

    @Override
    public void exitNull(SelescriptParser.NullContext ctx) {
        // Use a null string constant, otherwise complier will complain 
        // in situation such as println(null)
        prop.put(ctx, "$$NULL");
    }

    @Override
    public void exitFind(SelescriptParser.FindContext ctx) {
        String s = "find("
                + prop.get(ctx.stringval(0))
                + ","
                + prop.get(ctx.stringval(1))
                + ")";
        prop.put(ctx, s);
    }

    @Override
    public void exitReplace(SelescriptParser.ReplaceContext ctx) {
        String s = "replace("
                + prop.get(ctx.stringval(0))
                + ","
                + prop.get(ctx.stringval(1))
                + ","
                + prop.get(ctx.stringval(2))
                + ")";
        prop.put(ctx, s);
    }

}
